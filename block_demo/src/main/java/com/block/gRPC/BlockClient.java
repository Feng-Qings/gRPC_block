package com.block.gRPC;


import com.block.BlockProto;
import com.block.BlockServiceGrpc;
import com.block.model.Block;

import com.block.model.Transaction;
import com.block.utils.BlockConstant;
import com.block.utils.ProtobufBeanUtil;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;



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
     * @return 合格/不合格
     */
    public String sendToBoard(Transaction transaction){
        ManagedChannel channel = getChannel();
        try {
            BlockServiceGrpc.BlockServiceBlockingStub blockingStub = BlockServiceGrpc.newBlockingStub(channel);
            BlockProto.Msg msg = blockingStub.sendToBoard(ProtobufBeanUtil.transactionToProto(transaction));
            return msg.getData();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }finally {
            channel.shutdown();
        }
        return BlockConstant.UNQUALIFIED;
    }

    /**
     *  对新区块进行广播
     * @param block 区块
     */
    public void broadcast(Block block){
        Long size = stringRedisTemplate.opsForList().size("addresses");
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


}
