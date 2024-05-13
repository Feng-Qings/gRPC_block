package com.block.gRPC;

import com.alibaba.fastjson2.JSON;
import com.block.ModelProto;
import com.block.ModelServiceGrpc;
import com.block.model.Block;
import com.block.model.Transaction;
import com.block.utils.BlockConstant;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

//@ConfigurationProperties(prefix = "python")
@Component
@RequiredArgsConstructor
@Slf4j
public class ModelClient {
    @Value("${python.port}")
    private int port;
    @Value("${python.ip}")
    private String ip;
    private final StringRedisTemplate stringRedisTemplate;
    public ManagedChannel getChannel(){
        return ManagedChannelBuilder.forAddress(ip, port).usePlaintext().build();
    }
    public void sendModel(){
        ManagedChannel channel = getChannel();
        try {
            String hash = stringRedisTemplate.opsForList().index(BlockConstant.BLOCK_Addresses, -1);
            assert hash != null;
            String blockString = stringRedisTemplate.opsForValue().get(hash);
            Block block = JSON.parseObject(blockString, Block.class);
            assert block != null;
            Transaction transaction = block.getTransactions().get(1);
            ByteString model = ByteString.copyFrom(transaction.getBusinessInfo());
            ModelServiceGrpc.ModelServiceFutureStub futureStub = ModelServiceGrpc.newFutureStub(channel);
            ListenableFuture<ModelProto.Msg> msgListenableFuture = futureStub.latestModel(ModelProto.ModelState.newBuilder().setModel(model).build());
            Futures.addCallback(msgListenableFuture, new FutureCallback<ModelProto.Msg>() {
                @Override
                public void onSuccess(ModelProto.Msg result) {
                    log.info(result.getData());
                }

                @Override
                public void onFailure(Throwable t) {
                    log.error("模型传输错误!");
                }
            }, Executors.newCachedThreadPool());
//            ModelServiceGrpc.ModelServiceBlockingStub stub = ModelServiceGrpc.newBlockingStub(channel);
//            ModelProto.Msg msg = stub.latestModel(ModelProto.ModelState.newBuilder().setModel(model).build());
//            System.out.println(msg.getData());
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }finally {
            channel.shutdown();
        }
    }

    public double testModel(ByteString model){
        ManagedChannel channel = getChannel();
        try {
            ModelServiceGrpc.ModelServiceBlockingStub stub = ModelServiceGrpc.newBlockingStub(channel);
            ModelProto.Accuracy accuracy = stub.testModel(ModelProto.ModelState.newBuilder().setModel(model).build());
            return accuracy.getAcc();
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }finally {
            channel.shutdown();
        }
        return 0;
    }

    public void notice(String msg){
        ManagedChannel channel = getChannel();
        try {
            ModelServiceGrpc.ModelServiceFutureStub futureStub = ModelServiceGrpc.newFutureStub(channel);
            ListenableFuture<ModelProto.Msg> listenableFuture = futureStub.notice(ModelProto.Msg.newBuilder().setData(msg).build());
            Futures.addCallback(listenableFuture, new FutureCallback<ModelProto.Msg>() {
                @Override
                public void onSuccess(ModelProto.Msg result) {
                    log.info(result.getData());
                }

                @Override
                public void onFailure(Throwable t) {
                    log.error("notice error");
                }
            }, Executors.newCachedThreadPool());
            Thread.sleep(100);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            channel.shutdown();
        }
    }

//    public void notice(String msg){
//        ManagedChannel channel = getChannel();
//        try {
//            ModelServiceGrpc.ModelServiceBlockingStub stub = ModelServiceGrpc.newBlockingStub(channel);
//            ModelProto.Msg notice = stub.notice(ModelProto.Msg.newBuilder().setData(msg).build());
//            System.out.println(notice.getData());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }finally {
//            channel.shutdown();
//        }
//    }
}
