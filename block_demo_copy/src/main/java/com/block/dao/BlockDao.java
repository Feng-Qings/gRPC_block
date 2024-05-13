package com.block.dao;

import com.alibaba.fastjson.JSON;
import com.block.model.Block;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BlockDao {
    private final StringRedisTemplate stringRedisTemplate;

    public void saveBlock(Block block){
        String jsonString = JSON.toJSONString(block);
        stringRedisTemplate.opsForValue().set(block.getHash(), jsonString);
        stringRedisTemplate.opsForList().rightPush("blockAddresses",block.getHash());
    }
}
