package com.block.utils;


import com.block.model.Block;
import com.block.model.Transaction;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
@Component
public class BlockCache {
    private List<Block> blocks = new CopyOnWriteArrayList<>();
    private List<Transaction> transactions = new CopyOnWriteArrayList<>();


    public Block getLatestBlock() {
        return !blocks.isEmpty() ? blocks.get(blocks.size() - 1) : null;
    }
}
