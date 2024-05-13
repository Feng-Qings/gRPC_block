package com.block.gRPC;

import com.block.ModelProto;
import com.block.ModelServiceGrpc;
import com.block.model.Block;
import com.block.model.Transaction;
import com.block.service.BlockService;
import com.block.utils.BlockCache;
import com.block.utils.BlockConstant;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@GrpcService
@RequiredArgsConstructor
public class ModelServer extends ModelServiceGrpc.ModelServiceImplBase {
    private final BlockCache blockCache;
    private final BlockService blockService;
    private final BlockClient blockClient;
    private final ModelClient modelClient;
//    @Override
//    public void latestModel(ModelProto.ModelState request, StreamObserver<ModelProto.Msg> responseObserver) {
//        ByteString model = request.getModel();
//
//        List<Transaction> transactions = new ArrayList<>();
//        Transaction transaction = new Transaction();
//        transaction.setId(1);
//        transaction.setBusinessInfo(model.toByteArray());
//        transactions.add(transaction);
//        Random random = new Random();
//        int nonce = random.nextInt(Integer.MAX_VALUE);
//        String newBlockHash = blockService.calculateHash(blockCache.getLatestBlock().getHash(), transactions, nonce);
//        Block block = blockService.createNewBlock(nonce, blockCache.getLatestBlock().getHash(), newBlockHash, transactions);
//        blockClient.broadcast(block);
//        responseObserver.onNext(ModelProto.Msg.newBuilder().setData("模型接收成功！").build());
//        responseObserver.onCompleted();
//    }


    @Override
    public void latestModel(ModelProto.ModelState request, StreamObserver<ModelProto.Msg> responseObserver) {
        ByteString model = request.getModel();
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setId(1);
        transaction.setBusinessInfo(model.toByteArray());
        //将model发送给board判断最新model准确度
        String valid = blockClient.sendToBoard(transaction);
        modelClient.notice(valid);
        if (BlockConstant.QUALIFIED.equals(valid)){
            transactions.add(transaction);
            Random random = new Random();
            int nonce = random.nextInt(Integer.MAX_VALUE);
            String newBlockHash = blockService.calculateHash(blockCache.getLatestBlock().getHash(), transactions, nonce);
            Block block = blockService.createNewBlock(nonce, blockCache.getLatestBlock().getHash(), newBlockHash, transactions);
            blockClient.broadcast(block);
        }
        responseObserver.onNext(ModelProto.Msg.newBuilder().setData(valid).build());
        responseObserver.onCompleted();
    }
}
