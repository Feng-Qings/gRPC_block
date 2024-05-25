package com.block.gRPC;

import com.block.BlockProto;
import com.block.BlockServiceGrpc;
import com.block.model.Block;
import com.block.service.BlockService;
import com.block.utils.BlockCache;
import com.block.utils.ProtobufBeanUtil;
import com.google.protobuf.ByteString;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@RequiredArgsConstructor
@GrpcService
@Slf4j
public class BlockServer extends BlockServiceGrpc.BlockServiceImplBase {
    private final BlockCache blockCache;
    private final BlockService blockService;
    private final BlockClient blockClient;
    private final ModelClient modelClient;
    private final StringRedisTemplate stringRedisTemplate;

    /**
     *  接收最新区块
     */
    @Override
    public void responseLatestBlock(Empty request, StreamObserver<BlockProto.Block> responseObserver) {
        Block lastBlock = blockCache.getBlocks().get(blockCache.getBlocks().size() - 1);
        BlockProto.Block block = ProtobufBeanUtil.blockToProto(lastBlock);
        responseObserver.onNext(block);
        responseObserver.onCompleted();
    }

    /**
     *  接收整条链
     */
    @Override
    public void responseBlockChain(Empty request, StreamObserver<BlockProto.BlockChain> responseObserver) {
        List<Block> blocks = blockCache.getBlocks();
        BlockProto.BlockChain blockChain = ProtobufBeanUtil.blockChainToProto(blocks);
        responseObserver.onNext(blockChain);
        responseObserver.onCompleted();
    }

    /**
     *  接收广播的模型
     */
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
        modelClient.sendModel();
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
        responseObserver.onNext(BlockProto.Msg.newBuilder().setData(String.valueOf(acc)).build());
        responseObserver.onCompleted();
    }

    @Override
    public void notice(BlockProto.Msg request, StreamObserver<BlockProto.Msg> responseObserver) {
        modelClient.notice(request.getData());
    }
}
