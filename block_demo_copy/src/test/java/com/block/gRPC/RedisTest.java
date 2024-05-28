package com.block.gRPC;

import com.block.ModelServiceGrpc;
import com.block.gRPC.ModelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisTest {
    @Autowired
    ModelClient modelClient;
    @Test
    public void test2(){
//        modelClient.notice("开始训练");
    }
}