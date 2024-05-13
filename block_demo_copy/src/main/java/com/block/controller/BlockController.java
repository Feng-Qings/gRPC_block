package com.block.controller;

import com.alibaba.fastjson.JSON;
import com.block.gRPC.ModelClient;
import com.block.model.Block;
import com.block.service.BlockService;

import com.block.service.PowService;
import com.block.utils.BlockCache;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BlockController {
    private final BlockService blockService;
    private final BlockCache blockCache;
    private final PowService powService;
    private final ModelClient modelClient;

    @GetMapping("/blocks")
    public String getBlocks() {
        return JSON.toJSONString(blockCache.getBlocks());
    }


    @PostMapping("/create")
    public String createBlock() {
        Block genesisBlock = blockService.createGenesisBlock();
        return JSON.toJSONString(genesisBlock);
    }

    @PostMapping("/mine")
    public String mineBlock() {
        Block block = powService.mine();
        return JSON.toJSONString(block);
    }

    @GetMapping("/train")
    public void startTrain(){
        modelClient.notice("开始训练");
    }
}
