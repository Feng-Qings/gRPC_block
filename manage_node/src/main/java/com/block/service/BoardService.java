package com.block.service;

import com.block.utils.BlockConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final StringRedisTemplate stringRedisTemplate;

    public void setRandomBoard(){
        Long size = stringRedisTemplate.opsForList().size(BlockConstant.IP_Addresses);
        long rate = size/3;
        Random random = new Random();
        for (int i = 0; i <= rate; i++) {
            int index = random.nextInt(Math.toIntExact(size));
            String ip = stringRedisTemplate.opsForList().index(BlockConstant.IP_Addresses, index);
            log.info("委员会成员地址：" + ip);
            assert ip != null;
            stringRedisTemplate.opsForList().rightPush(BlockConstant.BOARD_Addresses, ip);
        }
    }
}
