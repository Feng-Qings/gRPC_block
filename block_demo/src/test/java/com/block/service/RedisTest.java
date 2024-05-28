package com.block.service;


import com.alibaba.fastjson2.JSON;
import com.block.ModelProto;
import com.block.ModelServiceGrpc;
import com.block.gRPC.ModelClient;
import com.block.model.Block;
import com.block.model.Transaction;
import com.block.utils.BlockConstant;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.Executors;

@SpringBootTest
public class RedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    ModelClient modelClient;

//    @Test
//    public void test1(){
//        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 9002).usePlaintext().build();
//        try {
//            String hash = stringRedisTemplate.opsForList().index(BlockConstant.BLOCK_Addresses, -1);
//            assert hash != null;
//            String blockString = stringRedisTemplate.opsForValue().get(hash);
//            Block block = JSON.parseObject(blockString, Block.class);
//            assert block != null;
//            Transaction transaction = block.getTransactions().get(0);
//            ByteString model = ByteString.copyFrom(transaction.getBusinessInfo());
//            ModelServiceGrpc.ModelServiceFutureStub futureStub = ModelServiceGrpc.newFutureStub(channel);
//            ListenableFuture<ModelProto.Msg> msgListenableFuture = futureStub.latestModel(ModelProto.ModelInfo.newBuilder().setModel(model).build());
//            Futures.addCallback(msgListenableFuture, new FutureCallback<ModelProto.Msg>() {
//                @Override
//                public void onSuccess(ModelProto.Msg result) {
//                    System.out.println("success");
//                }
//                @Override
//                public void onFailure(Throwable t) {
//                    System.out.println("error");
//                }
//            }, Executors.newCachedThreadPool());
//            Thread.sleep(100);
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            channel.shutdown();
//        }
//    }

//    @Test
//    public void test2(){
//        modelClient.notice("开始训练");
//    }
}
