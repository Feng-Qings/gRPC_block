package com.block.utils;


import com.block.BlockProto;
import com.block.model.Block;
import com.block.model.Transaction;
import com.google.protobuf.ByteString;


import java.util.List;
import java.util.stream.Collectors;

public class ProtobufBeanUtil {
    public static BlockProto.Transaction transactionToProto(Transaction transaction){
        return BlockProto.Transaction.newBuilder()
                .setId(transaction.getId())
                .setBusinessInfo(ByteString.copyFrom(transaction.getBusinessInfo()))
                .build();
    }

    public static BlockProto.Block blockToProto(Block block){
        BlockProto.Block.Builder builder = BlockProto.Block.newBuilder()
                .setIndex(block.getIndex())
                .setHash(block.getHash())
                .setPreHash(block.getPreviousHash())
                .setTimestamp(block.getTimestamp())
                .setNonce(block.getNonce());
        List<Transaction> transactions = block.getTransactions();
        transactions.forEach(transaction -> builder.addTransactions(transactionToProto(transaction)));
        return builder.build();
    }
    public static BlockProto.BlockChain blockChainToProto(List<Block> blocks){
        BlockProto.BlockChain.Builder builder = BlockProto.BlockChain.newBuilder();
        blocks.forEach(block -> builder.addBlocks(blockToProto(block)));
        return builder.build();
    }

    public static Transaction protoToTransaction(BlockProto.Transaction transaction){
        return new Transaction(transaction.getId(), transaction.getBusinessInfo().toByteArray());
    }

    public static Block protoToBlock(BlockProto.Block block){
        List<Transaction> transactions = block.getTransactionsList().stream().map(ProtobufBeanUtil::protoToTransaction).collect(Collectors.toList());
        return new Block(block.getIndex(),block.getHash(),block.getPreHash(),block.getTimestamp(),block.getNonce(),transactions);
    }


    public static List<Block> protoToBlockChain(BlockProto.BlockChain blockChain){
        return blockChain.getBlocksList().stream().map(ProtobufBeanUtil::protoToBlock).collect(Collectors.toList());
    }
}
