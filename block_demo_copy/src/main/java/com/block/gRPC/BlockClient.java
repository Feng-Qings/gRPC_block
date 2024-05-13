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

    public String sendToBoard(Transaction transaction){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 9000).usePlaintext().build();
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
    public void broadcast(Block block){
        Long size = stringRedisTemplate.opsForList().size("addresses");
        List<String> addresses = stringRedisTemplate.opsForList().range("addresses", 0, size - 1);
        BlockProto.Block blockProto = ProtobufBeanUtil.blockToProto(block);
        assert addresses != null;
        for (String address : addresses) {
            String[] strings = address.split(":");
            sendBlock(blockProto, strings[0], Integer.parseInt(strings[1]));
        }
    }

    @Async
    public void sendBlock(BlockProto.Block block, String address, int port){
        ManagedChannel channel = ManagedChannelBuilder.forAddress(address, port).usePlaintext().build();
        try {
            BlockServiceGrpc.BlockServiceBlockingStub blockServiceBlockingStub = BlockServiceGrpc.newBlockingStub(channel);
            blockServiceBlockingStub.broadcast(block);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }finally {
            channel.shutdown();
        }
    }
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
