package com.block.service;

import com.alibaba.fastjson.JSON;
import com.block.dao.BlockDao;
import com.block.gRPC.BlockClient;
import com.block.model.Block;
import com.block.model.Transaction;
import com.block.utils.BlockCache;
import com.block.utils.BlockConstant;
import com.block.utils.CommonUtil;
import com.block.utils.CryptoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockService implements ApplicationRunner {

    private final BlockCache blockCache;
    private final BlockClient blockClient;
    private final BlockDao blockDao;
    private final StringRedisTemplate stringRedisTemplate;

    @Value("${grpc.server.port}")
    private String port;

    /**
     * 创世区块
     */
    public Block createGenesisBlock() {
        Block genesisBlock = new Block();
        //设置创世区块高度为1
        genesisBlock.setIndex(1);
        genesisBlock.setTimestamp(System.currentTimeMillis());
        genesisBlock.setNonce(1);
        //封装业务数据
        List<Transaction> tsaList = new ArrayList<>();
        Transaction tsa = new Transaction(1, "这是创世区块".getBytes());
        tsaList.add(tsa);
        Transaction tsa2 = new Transaction(2, "区块链高度为：1".getBytes());
        tsaList.add(tsa2);
        genesisBlock.setTransactions(tsaList);
        //设置创世区块的hash值
        genesisBlock.setHash(calculateHash("",tsaList,1));
        genesisBlock.setPreviousHash("");
        //添加到区块链中
        blockCache.getBlocks().add(genesisBlock);
        //保存到redis中
        blockDao.saveBlock(genesisBlock);
//        rpcClient.broadcast(genesisBlock);
        return genesisBlock;
    }

    /**
     * 创建新区块
     */
    public Block createNewBlock(int nonce, String previousHash, String hash, List<Transaction> blockTxs) {
        Block block = new Block();
        block.setIndex(blockCache.getBlocks().size() + 1);
        //时间戳
        block.setTimestamp(System.currentTimeMillis());
        block.setTransactions(blockTxs);
        //工作量证明，计算正确hash值的次数
        block.setNonce(nonce);
        //上一区块的哈希
        block.setPreviousHash(previousHash);
        //当前区块的哈希
        block.setHash(hash);
        if (addBlock(block)) {
            return block;
        }
        return null;
    }


    public void replaceChain(List<Block> newBlocks) {
        List<Block> localBlockChain = blockCache.getBlocks();
        if (isValidChain(newBlocks) && newBlocks.size() > localBlockChain.size()) {
            localBlockChain = newBlocks;
            blockCache.setBlocks(localBlockChain);
            System.out.println("替换后的本节点区块链："+JSON.toJSONString(blockCache.getBlocks()));
        } else {
            System.out.println("接收的区块链无效");
        }
    }

    /**
     * 添加新区块到当前节点的区块链中
     */
    public boolean addBlock(Block newBlock) {
        //先对新区块的合法性进行校验
        if (isValidNewBlock(newBlock, blockCache.getLatestBlock())) {
            blockCache.getBlocks().add(newBlock);
            blockCache.getTransactions().clear();
            blockDao.saveBlock(newBlock);
            return true;
        }
        return false;
    }


    /**
     * 验证新区块是否有效
     */
    public boolean isValidNewBlock(Block newBlock, Block previousBlock) {
        if (!previousBlock.getHash().equals(newBlock.getPreviousHash())) {
            System.out.println("新区块的前一个区块hash验证不通过");
            return false;
        } else {
            // 验证新区块hash值的正确性
            String hash = calculateHash(newBlock.getPreviousHash(), newBlock.getTransactions(), newBlock.getNonce());
            if (!hash.equals(newBlock.getHash())) {
                System.out.println("新区块的hash无效: " + hash + " " + newBlock.getHash());
                return false;
            }
//            return isValidHash(newBlock.getHash());
            return true;
        }
    }

    /**
     * 验证hash值是否满足系统条件
     */
    public boolean isValidHash(String hash) {
        return hash.startsWith("0000");
    }

    /**
     * 验证整个区块链是否有效
     */
    public boolean isValidChain(List<Block> chain) {
        Block block = null;
        Block lastBlock = chain.get(0);
        int currentIndex = 1;
        while (currentIndex < chain.size()) {
            block = chain.get(currentIndex);
            if (!isValidNewBlock(block, lastBlock)) {
                return false;
            }
            lastBlock = block;
            currentIndex++;
        }
        return true;
    }

    /**
     * 计算区块的hash
     */
    public String calculateHash(String previousHash, List<Transaction> currentTransactions, int nonce) {
        return CryptoUtil.SHA256(previousHash + JSON.toJSONString(currentTransactions) + nonce);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Long size = stringRedisTemplate.opsForList().size(BlockConstant.IP_Addresses);
        String localIp = CommonUtil.getLocalIp();
        if (size == null || size == 0){
            stringRedisTemplate.opsForList().rightPush(BlockConstant.IP_Addresses, localIp + ":" + port);
            if (blockCache.getBlocks().isEmpty()){
                this.createGenesisBlock();
            }
        }else {
            String address = stringRedisTemplate.opsForList().index(BlockConstant.IP_Addresses, size - 1);
            assert address != null;
            String[] strings = address.split(":");
            blockClient.setIp(strings[0]);
            blockClient.setPort(Integer.parseInt(strings[1]));
            List<Block> blocks = blockClient.getBlocks();
            blockCache.setBlocks(blocks);
            stringRedisTemplate.opsForList().rightPush(BlockConstant.IP_Addresses, localIp + ":" + port);
        }
    }
}
