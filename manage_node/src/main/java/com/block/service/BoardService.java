package com.block.service;

import com.block.BlockProto;
import com.block.BlockServiceGrpc;
import com.block.utils.BlockConstant;
import com.block.utils.NoticeConstant;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final StringRedisTemplate stringRedisTemplate;

    public Set<Integer> setRandomBoard(){
        Long size = stringRedisTemplate.opsForList().size(BlockConstant.IP_Addresses);
        assert size != null;
        long rate = size / 3;
        Random random = new Random();
        Set<Integer> boards = new HashSet<>();
        for (int i = 0; i <= rate; i++) {
            int index = random.nextInt(Math.toIntExact(size));
            String ip = stringRedisTemplate.opsForList().index(BlockConstant.IP_Addresses, index);
            log.info("委员会成员地址：" + ip);
            assert ip != null;
            stringRedisTemplate.opsForList().rightPush(BlockConstant.BOARD_Addresses, ip);
            boards.add(index);
        }
        return boards;
    }
    public void startTrain(){
        stringRedisTemplate.opsForValue().set(BlockConstant.GLOBAL_STATE, BlockConstant.GLOBAL_TRAIN);
        Long size = stringRedisTemplate.opsForList().size(BlockConstant.IP_Addresses);
        Set<Integer> boards = setRandomBoard();
        assert size != null;
        for (int i = 0; i < size; i++) {
            if (!boards.contains(i)){
                nodeTrain(i);
            }
        }
    }
    public void nodeTrain(int index){
        String address = stringRedisTemplate.opsForList().index(BlockConstant.IP_Addresses, index);
        assert address != null;
        String[] split = address.split(":");
        ManagedChannel channel = ManagedChannelBuilder.forAddress(split[0], Integer.parseInt(split[1])).usePlaintext().build();
        try {
            BlockServiceGrpc.BlockServiceFutureStub futureStub = BlockServiceGrpc.newFutureStub(channel);
            ListenableFuture<BlockProto.Msg> listenableFuture = futureStub.notice(BlockProto.Msg.newBuilder().setData(NoticeConstant.START_TRAIN).build());
            Futures.addCallback(listenableFuture, new FutureCallback<BlockProto.Msg>() {
                @Override
                public void onSuccess(BlockProto.Msg result) {
                    log.info("start train");
                }
                @Override
                public void onFailure(Throwable t) {
                    log.error("train error",t);
                }
            }, Executors.newCachedThreadPool());
            Thread.sleep(100);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }finally {
            channel.shutdown();
        }
    }
}
