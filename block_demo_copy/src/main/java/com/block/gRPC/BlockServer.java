package com.block.gRPC;

import com.block.BlockProto;
import com.block.BlockServiceGrpc;
import com.block.model.Block;
import com.block.service.BlockService;
import com.block.utils.BlockCache;
import com.block.utils.BlockConstant;
import com.block.utils.ProtobufBeanUtil;
import com.google.protobuf.ByteString;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@RequiredArgsConstructor
@GrpcService
public class BlockServer extends BlockServiceGrpc.BlockServiceImplBase {
    private final BlockCache blockCache;
    private final BlockService blockService;
    private final BlockClient blockClient;
    private final ModelClient modelClient;

    @Override
    public void responseLatestBlock(Empty request, StreamObserver<BlockProto.Block> responseObserver) {
        Block lastBlock = blockCache.getBlocks().get(blockCache.getBlocks().size() - 1);
        BlockProto.Block block = ProtobufBeanUtil.blockToProto(lastBlock);
        responseObserver.onNext(block);
        responseObserver.onCompleted();
    }

    @Override
    public void responseBlockChain(Empty request, StreamObserver<BlockProto.BlockChain> responseObserver) {
        List<Block> blocks = blockCache.getBlocks();
        BlockProto.BlockChain blockChain = ProtobufBeanUtil.blockChainToProto(blocks);
        responseObserver.onNext(blockChain);
        responseObserver.onCompleted();
    }

    @Override
    public void broadcast(BlockProto.Block request, StreamObserver<Empty> responseObserver) {
        Block latestBlockReceived = ProtobufBeanUtil.protoToBlock(request);
        if (latestBlockReceived.getIndex() == 1 && blockCache.getBlocks().isEmpty()) {
            blockCache.getBlocks().add(latestBlockReceived);
        }else {
            Block latestBlock = blockCache.getBlocks().get(blockCache.getBlocks().size() - 1);
            if (latestBlockReceived.getIndex() == latestBlock.getIndex() + 1 && latestBlockReceived.getPreviousHash().equals(latestBlock.getHash())){
                blockService.addBlock(latestBlockReceived);
            } else if (latestBlockReceived.getIndex() > latestBlock.getIndex() + 1) {
                List<Block> blocks = blockClient.getBlocks();
                if (blocks.size() > blockCache.getBlocks().size()) {
                    blockCache.setBlocks(blocks);
                }
            }
        }
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    /**
     *  对模型进行验证
     */
    @Override
    public void sendToBoard(BlockProto.Transaction request, StreamObserver<BlockProto.Msg> responseObserver) {
        ByteString model = request.getBusinessInfo();
        double acc = modelClient.testModel(model);
        if (acc >= 0.7){
            responseObserver.onNext(BlockProto.Msg.newBuilder().setData(BlockConstant.QUALIFIED).build());
        }else {
            responseObserver.onNext(BlockProto.Msg.newBuilder().setData(BlockConstant.UNQUALIFIED).build());
        }
        responseObserver.onCompleted();
    }
}
