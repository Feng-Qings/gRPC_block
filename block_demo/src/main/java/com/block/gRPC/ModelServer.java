package com.block.gRPC;

import com.block.BlockProto;
import com.block.BlockServiceGrpc;
import com.block.ModelProto;
import com.block.ModelServiceGrpc;
import com.block.model.Block;
import com.block.model.Contributor;
import com.block.model.Transaction;
import com.block.service.BlockService;
import com.block.utils.BlockCache;
import com.block.utils.BlockConstant;
import com.block.utils.NoticeConstant;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;
import java.util.concurrent.Executors;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class ModelServer extends ModelServiceGrpc.ModelServiceImplBase {
    @Value("${python.port}")
    private int port;
    @Value("${python.ip}")
    private String ip;
    private final BlockCache blockCache;
    private final BlockService blockService;
    private final BlockClient blockClient;
    private final ModelClient modelClient;
    private final StringRedisTemplate stringRedisTemplate;

    /**
     *获取最新模型只需验证，不用广播到所有节点
     */
    @Override
    public void latestModel(ModelProto.ModelInfo request, StreamObserver<ModelProto.Msg> responseObserver) {
        ByteString model = request.getModel();
        String address = request.getAddress();
        Transaction transaction = new Transaction();
        transaction.setId(1);
        transaction.setBusinessInfo(model.toByteArray());

        String state = stringRedisTemplate.opsForValue().get(BlockConstant.GLOBAL_STATE);
        //根据全局状态判断是否继续训练
        if (BlockConstant.GLOBAL_TRAIN.equals(state)){
            //将model发送给board判断最新model准确度
            double v = blockClient.sendToTest(transaction);
            if (v > 0.7){
                //TODO 贡献度暂使用准确度相加
                Object o = stringRedisTemplate.opsForHash().get(BlockConstant.CONTRIBUTORS, address);
                double contribute = o == null ? 0 : Double.parseDouble(o.toString());
                contribute += v;
                stringRedisTemplate.opsForHash().put(BlockConstant.CONTRIBUTORS,address,String.valueOf(contribute));
                modelClient.notice(BlockConstant.QUALIFIED);
            }else {
                modelClient.notice(BlockConstant.UNQUALIFIED);
            }
            responseObserver.onNext(ModelProto.Msg.newBuilder().setData(String.valueOf(v)).build());
            responseObserver.onCompleted();
        }else {
            responseObserver.onNext(ModelProto.Msg.newBuilder().setData(NoticeConstant.STOP_TRAIN).build());
            responseObserver.onCompleted();
        }

    }

    /**
     *获取的全局模型需要广播到所有节点
     */
    @Override
    public void globalModel(ModelProto.ModelState request, StreamObserver<ModelProto.Msg> responseObserver) {
        //停止训练
        stringRedisTemplate.opsForValue().set(BlockConstant.GLOBAL_STATE, BlockConstant.GLOBAL_STOP);
        //stopAll();

        ByteString model = request.getModel();
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setId(1);
        transaction.setBusinessInfo(model.toByteArray());
        transactions.add(transaction);
        Random random = new Random();
        int nonce = random.nextInt(Integer.MAX_VALUE);
        String newBlockHash = blockService.calculateHash(blockCache.getLatestBlock().getHash(), transactions, nonce);
        Block block = blockService.createNewBlock(nonce, blockCache.getLatestBlock().getHash(), newBlockHash, transactions);
        blockClient.broadcast(block);
        //重置委员会
        resetBoard();
        //开始训练
        stringRedisTemplate.opsForValue().set(BlockConstant.GLOBAL_STATE, BlockConstant.GLOBAL_TRAIN);
        responseObserver.onNext(ModelProto.Msg.newBuilder().setData("全局模型接收成功").build());
        responseObserver.onCompleted();

    }

    /**
     * 重置委员会
     */
    public void resetBoard(){
        log.info("重置委员会");
        stringRedisTemplate.delete(BlockConstant.BOARD_Addresses);
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(BlockConstant.CONTRIBUTORS);
        Set<Object> objects = entries.keySet();
        List<Contributor> contributors = new ArrayList<>();
        for (Object object : objects) {
            Contributor contributor = new Contributor();
            contributor.setAddress(object.toString());
            contributor.setValue(entries.get(object).toString());
            contributors.add(contributor);
        }
        //根据贡献值排序
        contributors.sort(new Comparator<Contributor>() {
            @Override
            public int compare(Contributor o1, Contributor o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        Set<String> boards = new HashSet<>();
        // TODO 暂定委员只有1个
        for (int i = 0; i < 1; i++) {
            boards.add(contributors.get(i).getAddress());
            log.info("委员会成员：" + contributors.get(i).getAddress());
            stringRedisTemplate.opsForList().rightPush(BlockConstant.BOARD_Addresses, contributors.get(i).getAddress());
        }
        blockClient.initBoard();
        log.info("重置委员会完成");
        resetTrain(boards);
    }
    /**
     * 暂停所有训练
     */
    public void stopAll(){
        Long size = stringRedisTemplate.opsForList().size(BlockConstant.IP_Addresses);
        assert size != null;
        List<String> addresses = stringRedisTemplate.opsForList().range(BlockConstant.IP_Addresses, 0, size - 1);
        assert addresses != null;
        for (String address : addresses) {
           nodeNotice(address, NoticeConstant.STOP_TRAIN);
        }
    }

    /**
     * 非委员开始训练
     * @param boards 委员会地址
     */
    public void resetTrain(Set<String> boards){
        log.info("开始下一轮");
        Long size = stringRedisTemplate.opsForList().size(BlockConstant.IP_Addresses);
        assert size != null;
        List<String> addresses = stringRedisTemplate.opsForList().range(BlockConstant.IP_Addresses, 0, size - 1);
        assert addresses != null;
        for (String address : addresses) {
            if (!boards.contains(address)){
                nodeNotice(address, NoticeConstant.START_TRAIN);
            }
        }
    }

    /**
     * 通知节点
     * @param address 地址
     * @param msg     要发送的消息
     */
    public void nodeNotice(String address, String msg){
        String[] split = address.split(":");
        ManagedChannel channel = ManagedChannelBuilder.forAddress(split[0], Integer.parseInt(split[1])).usePlaintext().build();
        try {
            BlockServiceGrpc.BlockServiceFutureStub futureStub = BlockServiceGrpc.newFutureStub(channel);
            ListenableFuture<BlockProto.Msg> listenableFuture = futureStub.notice(BlockProto.Msg.newBuilder().setData(msg).build());
            Futures.addCallback(listenableFuture, new FutureCallback<BlockProto.Msg>() {
                @Override
                public void onSuccess(BlockProto.Msg result) {

                }
                @Override
                public void onFailure(Throwable t) {

                }
            }, Executors.newCachedThreadPool());
            Thread.sleep(100);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }finally {
            channel.shutdown();
        }
    }

    //TODO 结束训练
    public void finishTrain(){
        Long size = stringRedisTemplate.opsForList().size(BlockConstant.IP_Addresses);
        assert size != null;
        List<String> addresses = stringRedisTemplate.opsForList().range(BlockConstant.IP_Addresses, 0, size - 1);
        assert addresses != null;
        for (String address : addresses) {
           nodeNotice(address, NoticeConstant.FINISH);
        }
    }

    @Override
    public void init(ModelProto.initData request, StreamObserver<ModelProto.Msg> responseObserver) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(ip, port).usePlaintext().build();
        try {
            ModelServiceGrpc.ModelServiceBlockingStub stub = ModelServiceGrpc.newBlockingStub(channel);
            ModelProto.Msg msg = stub.init(request);
            log.info(msg.getData());
            responseObserver.onNext(ModelProto.Msg.newBuilder().setData(msg.getData()).build());
            responseObserver.onCompleted();
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }finally {
            channel.shutdown();
        }
    }
}
