package com.block.gRPC;


import com.block.BlockProto;
import com.block.BlockServiceGrpc;
import com.block.ModelProto;
import com.block.ModelServiceGrpc;
import com.block.model.Block;

import com.block.model.Transaction;
import com.block.utils.BlockConstant;
import com.block.utils.ProtobufBeanUtil;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


@Component
@Data
@Slf4j
@RequiredArgsConstructor
public class BlockClient {
    private String ip;
    private int port;
    private final StringRedisTemplate stringRedisTemplate;


    public ManagedChannel getChannel(){
        return ManagedChannelBuilder.forAddress(ip, port).usePlaintext().build();
    }

    /**
     *  将模型发送给委员会验证
     * @param transaction 交易数据
     * @return 准确度
     * 发送给委员会，目前发送给开始获取区块的节点，同步
     */
//    public double sendToTest(Transaction transaction){
//        ManagedChannel channel = getChannel();
//        try {
//            BlockServiceGrpc.BlockServiceBlockingStub blockingStub = BlockServiceGrpc.newBlockingStub(channel);
//            BlockProto.Msg msg = blockingStub.testLeader(ProtobufBeanUtil.transactionToProto(transaction));
//            return Double.parseDouble(msg.getData());
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }finally {
//            channel.shutdown();
//        }
//        return 0;
//    }

    /**
     * 异步
     */
    public double sendToTest(Transaction transaction){
        Long size = stringRedisTemplate.opsForList().size(BlockConstant.BOARD_Addresses);
        assert size != null;
        List<String> boardAddress = stringRedisTemplate.opsForList().range(BlockConstant.BOARD_Addresses, 0, size - 1);
        BlockProto.Transaction transactionProto = ProtobufBeanUtil.transactionToProto(transaction);
        //异步任务列表
        List<CompletableFuture<Double>> futureList = new ArrayList<>();
        assert boardAddress != null;
        for (String address : boardAddress) {
            String[] split = address.split(":");
            CompletableFuture<Double> futureResult = boardTest(transactionProto, split[0], Integer.parseInt(split[1]));
            futureList.add(futureResult);
        }
        //等待所有异步任务完成
        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(
                futureList.toArray(new CompletableFuture[0])
        );
        //收集所有任务的结果
        CompletableFuture<List<Double>> allResultsFuture = allOfFuture.thenApply(v ->
                futureList.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
        );
        try {
            //获得结果
            List<Double> results = allResultsFuture.get();
            int length = results.size();
            log.info("length:" + length);
            //中位数
//            double mid = 0;
//            if (size % 2 == 1) {
//                mid = results.get(length / 2);
//            } else {
//                mid = (results.get(length / 2 - 1) + results.get(length / 2)) / 2.0;
//            }
//            return mid;
            return 0.8;
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return 0;
    }

    /**
     * 异步模型验证
     * @param transaction model交易
     * @param ip          委员IP地址
     * @param port        委员端口
     * @return CompletableFuture
     */
    @Async
    public CompletableFuture<Double> boardTest(BlockProto.Transaction transaction, String ip, int port){
        return CompletableFuture.supplyAsync(()->{
            ManagedChannel channel = ManagedChannelBuilder.forAddress(ip, port).usePlaintext().build();
            try {
                BlockServiceGrpc.BlockServiceBlockingStub blockServiceBlockingStub = BlockServiceGrpc.newBlockingStub(channel);
                BlockProto.Msg msg = blockServiceBlockingStub.sendToBoard(transaction);
                return Double.parseDouble(msg.getData());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }finally {
                channel.shutdown();
            }
        });
    }
    /**
     *  对新区块进行广播
     * @param block 区块
     */
    public void broadcast(Block block){
        Long size = stringRedisTemplate.opsForList().size("addresses");
        assert size != null;
        List<String> addresses = stringRedisTemplate.opsForList().range("addresses", 0, size - 1);
        assert addresses != null;
        for (String address : addresses) {
            String[] strings = address.split(":");
            sendBlock(block, strings[0], Integer.parseInt(strings[1]));
        }
    }

    /**
     * 发送区块
     * @param block 区块
     * @param address ip
     * @param port 端口
     */
    @Async
    public void sendBlock(Block block, String address, int port){
        ManagedChannel channel = ManagedChannelBuilder.forAddress(address, port).usePlaintext().build();
        try {
            BlockServiceGrpc.BlockServiceBlockingStub blockServiceBlockingStub = BlockServiceGrpc.newBlockingStub(channel);
            BlockProto.Block blockToProto = ProtobufBeanUtil.blockToProto(block);
            blockServiceBlockingStub.broadcast(blockToProto);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }finally {
            channel.shutdown();
        }
    }

    /**
     *  获得最新区块
     * @return 区块
     */
    public Block getLastBlock(){
        ManagedChannel channel = this.getChannel();
        BlockProto.Block block = null;
        try {
            BlockServiceGrpc.BlockServiceBlockingStub blockServiceBlockingStub = BlockServiceGrpc.newBlockingStub(channel);
            block = blockServiceBlockingStub.responseLatestBlock(Empty.newBuilder().build());
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }finally {
            channel.shutdown();
        }
        assert block != null;
        return ProtobufBeanUtil.protoToBlock(block);
    }

    /**
     *  获得整条链
     * @return blocks
     */
    public List<Block> getBlocks(){
        ManagedChannel channel = this.getChannel();
        BlockProto.BlockChain chain = null;
        try {
            BlockServiceGrpc.BlockServiceBlockingStub blockServiceBlockingStub = BlockServiceGrpc.newBlockingStub(channel);
            chain = blockServiceBlockingStub.responseBlockChain(Empty.newBuilder().build());
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }finally {
            channel.shutdown();
        }
        assert chain != null;
        return ProtobufBeanUtil.protoToBlockChain(chain);
    }


    /**
     * 初始化委员会
     */
    public void initBoard(){
        Long size = stringRedisTemplate.opsForList().size(BlockConstant.BOARD_Addresses);
        assert size != null;
        List<String> addresses = stringRedisTemplate.opsForList().range(BlockConstant.BOARD_Addresses, 0, size - 1);
        assert addresses != null : "委员会为null";
        for (String address : addresses) {
            String[] strings = address.split(":");
            init(strings[0], Integer.parseInt(strings[1]));
        }
    }

//    public void init(String address, int port){
//        ManagedChannel channel = ManagedChannelBuilder.forAddress(address, port).usePlaintext().build();
//        try {
//            ModelServiceGrpc.ModelServiceFutureStub futureStub = ModelServiceGrpc.newFutureStub(channel);
//            ModelProto.initData.Builder builder = ModelProto.initData.newBuilder();
//            builder.setAccThreshold(0.7);
//            builder.setModelNumThreshold(5);
//            ListenableFuture<ModelProto.Msg> listenableFuture = futureStub.init(builder.build());
//            Futures.addCallback(listenableFuture, new FutureCallback<ModelProto.Msg>() {
//                @Override
//                public void onSuccess(ModelProto.Msg result) {
//                    log.info(result.getData());
//                }
//
//                @Override
//                public void onFailure(Throwable t) {
//                    log.error(t.getMessage(),t);
//                }
//            }, Executors.newCachedThreadPool());
//            Thread.sleep(100);
//        }catch (Exception e){
//            log.error(e.getMessage(), e);
//        }finally {
//            channel.shutdown();
//        }
//    }
    @Async
    public void init(String address, int port){
        ManagedChannel channel = ManagedChannelBuilder.forAddress(address, port).usePlaintext().build();
        try {
            ModelServiceGrpc.ModelServiceBlockingStub stub = ModelServiceGrpc.newBlockingStub(channel);
            ModelProto.initData.Builder builder = ModelProto.initData.newBuilder();
            builder.setAccThreshold(0.7);
            builder.setModelNumThreshold(5);
            ModelProto.Msg msg = stub.init(builder.build());
            System.out.println(msg.getData());
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }finally {
            channel.shutdown();
        }
    }

}
