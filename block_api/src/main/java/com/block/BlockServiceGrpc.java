package com.block;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.63.0)",
    comments = "Source: block.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class BlockServiceGrpc {

  private BlockServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "block_service.BlockService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.block.BlockProto.Block> getResponseLatestBlockMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "responseLatestBlock",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.block.BlockProto.Block.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.block.BlockProto.Block> getResponseLatestBlockMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.block.BlockProto.Block> getResponseLatestBlockMethod;
    if ((getResponseLatestBlockMethod = BlockServiceGrpc.getResponseLatestBlockMethod) == null) {
      synchronized (BlockServiceGrpc.class) {
        if ((getResponseLatestBlockMethod = BlockServiceGrpc.getResponseLatestBlockMethod) == null) {
          BlockServiceGrpc.getResponseLatestBlockMethod = getResponseLatestBlockMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.block.BlockProto.Block>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "responseLatestBlock"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.block.BlockProto.Block.getDefaultInstance()))
              .setSchemaDescriptor(new BlockServiceMethodDescriptorSupplier("responseLatestBlock"))
              .build();
        }
      }
    }
    return getResponseLatestBlockMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.block.BlockProto.BlockChain> getResponseBlockChainMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "responseBlockChain",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.block.BlockProto.BlockChain.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.block.BlockProto.BlockChain> getResponseBlockChainMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.block.BlockProto.BlockChain> getResponseBlockChainMethod;
    if ((getResponseBlockChainMethod = BlockServiceGrpc.getResponseBlockChainMethod) == null) {
      synchronized (BlockServiceGrpc.class) {
        if ((getResponseBlockChainMethod = BlockServiceGrpc.getResponseBlockChainMethod) == null) {
          BlockServiceGrpc.getResponseBlockChainMethod = getResponseBlockChainMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.block.BlockProto.BlockChain>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "responseBlockChain"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.block.BlockProto.BlockChain.getDefaultInstance()))
              .setSchemaDescriptor(new BlockServiceMethodDescriptorSupplier("responseBlockChain"))
              .build();
        }
      }
    }
    return getResponseBlockChainMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.block.BlockProto.Block,
      com.google.protobuf.Empty> getBroadcastMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "broadcast",
      requestType = com.block.BlockProto.Block.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.block.BlockProto.Block,
      com.google.protobuf.Empty> getBroadcastMethod() {
    io.grpc.MethodDescriptor<com.block.BlockProto.Block, com.google.protobuf.Empty> getBroadcastMethod;
    if ((getBroadcastMethod = BlockServiceGrpc.getBroadcastMethod) == null) {
      synchronized (BlockServiceGrpc.class) {
        if ((getBroadcastMethod = BlockServiceGrpc.getBroadcastMethod) == null) {
          BlockServiceGrpc.getBroadcastMethod = getBroadcastMethod =
              io.grpc.MethodDescriptor.<com.block.BlockProto.Block, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "broadcast"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.block.BlockProto.Block.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new BlockServiceMethodDescriptorSupplier("broadcast"))
              .build();
        }
      }
    }
    return getBroadcastMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.block.BlockProto.Transaction,
      com.block.BlockProto.Msg> getSendToBoardMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "sendToBoard",
      requestType = com.block.BlockProto.Transaction.class,
      responseType = com.block.BlockProto.Msg.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.block.BlockProto.Transaction,
      com.block.BlockProto.Msg> getSendToBoardMethod() {
    io.grpc.MethodDescriptor<com.block.BlockProto.Transaction, com.block.BlockProto.Msg> getSendToBoardMethod;
    if ((getSendToBoardMethod = BlockServiceGrpc.getSendToBoardMethod) == null) {
      synchronized (BlockServiceGrpc.class) {
        if ((getSendToBoardMethod = BlockServiceGrpc.getSendToBoardMethod) == null) {
          BlockServiceGrpc.getSendToBoardMethod = getSendToBoardMethod =
              io.grpc.MethodDescriptor.<com.block.BlockProto.Transaction, com.block.BlockProto.Msg>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "sendToBoard"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.block.BlockProto.Transaction.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.block.BlockProto.Msg.getDefaultInstance()))
              .setSchemaDescriptor(new BlockServiceMethodDescriptorSupplier("sendToBoard"))
              .build();
        }
      }
    }
    return getSendToBoardMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.block.BlockProto.Msg,
      com.block.BlockProto.Msg> getNoticeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "notice",
      requestType = com.block.BlockProto.Msg.class,
      responseType = com.block.BlockProto.Msg.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.block.BlockProto.Msg,
      com.block.BlockProto.Msg> getNoticeMethod() {
    io.grpc.MethodDescriptor<com.block.BlockProto.Msg, com.block.BlockProto.Msg> getNoticeMethod;
    if ((getNoticeMethod = BlockServiceGrpc.getNoticeMethod) == null) {
      synchronized (BlockServiceGrpc.class) {
        if ((getNoticeMethod = BlockServiceGrpc.getNoticeMethod) == null) {
          BlockServiceGrpc.getNoticeMethod = getNoticeMethod =
              io.grpc.MethodDescriptor.<com.block.BlockProto.Msg, com.block.BlockProto.Msg>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "notice"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.block.BlockProto.Msg.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.block.BlockProto.Msg.getDefaultInstance()))
              .setSchemaDescriptor(new BlockServiceMethodDescriptorSupplier("notice"))
              .build();
        }
      }
    }
    return getNoticeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BlockServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BlockServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BlockServiceStub>() {
        @java.lang.Override
        public BlockServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BlockServiceStub(channel, callOptions);
        }
      };
    return BlockServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BlockServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BlockServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BlockServiceBlockingStub>() {
        @java.lang.Override
        public BlockServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BlockServiceBlockingStub(channel, callOptions);
        }
      };
    return BlockServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static BlockServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BlockServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BlockServiceFutureStub>() {
        @java.lang.Override
        public BlockServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BlockServiceFutureStub(channel, callOptions);
        }
      };
    return BlockServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void responseLatestBlock(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.block.BlockProto.Block> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getResponseLatestBlockMethod(), responseObserver);
    }

    /**
     */
    default void responseBlockChain(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.block.BlockProto.BlockChain> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getResponseBlockChainMethod(), responseObserver);
    }

    /**
     */
    default void broadcast(com.block.BlockProto.Block request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getBroadcastMethod(), responseObserver);
    }

    /**
     */
    default void sendToBoard(com.block.BlockProto.Transaction request,
        io.grpc.stub.StreamObserver<com.block.BlockProto.Msg> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendToBoardMethod(), responseObserver);
    }

    /**
     */
    default void notice(com.block.BlockProto.Msg request,
        io.grpc.stub.StreamObserver<com.block.BlockProto.Msg> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getNoticeMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service BlockService.
   */
  public static abstract class BlockServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return BlockServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service BlockService.
   */
  public static final class BlockServiceStub
      extends io.grpc.stub.AbstractAsyncStub<BlockServiceStub> {
    private BlockServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BlockServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BlockServiceStub(channel, callOptions);
    }

    /**
     */
    public void responseLatestBlock(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.block.BlockProto.Block> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getResponseLatestBlockMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void responseBlockChain(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.block.BlockProto.BlockChain> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getResponseBlockChainMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void broadcast(com.block.BlockProto.Block request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getBroadcastMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendToBoard(com.block.BlockProto.Transaction request,
        io.grpc.stub.StreamObserver<com.block.BlockProto.Msg> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendToBoardMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void notice(com.block.BlockProto.Msg request,
        io.grpc.stub.StreamObserver<com.block.BlockProto.Msg> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getNoticeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service BlockService.
   */
  public static final class BlockServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<BlockServiceBlockingStub> {
    private BlockServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BlockServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BlockServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.block.BlockProto.Block responseLatestBlock(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getResponseLatestBlockMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.block.BlockProto.BlockChain responseBlockChain(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getResponseBlockChainMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty broadcast(com.block.BlockProto.Block request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getBroadcastMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.block.BlockProto.Msg sendToBoard(com.block.BlockProto.Transaction request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendToBoardMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.block.BlockProto.Msg notice(com.block.BlockProto.Msg request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getNoticeMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service BlockService.
   */
  public static final class BlockServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<BlockServiceFutureStub> {
    private BlockServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BlockServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BlockServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.block.BlockProto.Block> responseLatestBlock(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getResponseLatestBlockMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.block.BlockProto.BlockChain> responseBlockChain(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getResponseBlockChainMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> broadcast(
        com.block.BlockProto.Block request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getBroadcastMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.block.BlockProto.Msg> sendToBoard(
        com.block.BlockProto.Transaction request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendToBoardMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.block.BlockProto.Msg> notice(
        com.block.BlockProto.Msg request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getNoticeMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_RESPONSE_LATEST_BLOCK = 0;
  private static final int METHODID_RESPONSE_BLOCK_CHAIN = 1;
  private static final int METHODID_BROADCAST = 2;
  private static final int METHODID_SEND_TO_BOARD = 3;
  private static final int METHODID_NOTICE = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_RESPONSE_LATEST_BLOCK:
          serviceImpl.responseLatestBlock((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<com.block.BlockProto.Block>) responseObserver);
          break;
        case METHODID_RESPONSE_BLOCK_CHAIN:
          serviceImpl.responseBlockChain((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<com.block.BlockProto.BlockChain>) responseObserver);
          break;
        case METHODID_BROADCAST:
          serviceImpl.broadcast((com.block.BlockProto.Block) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_SEND_TO_BOARD:
          serviceImpl.sendToBoard((com.block.BlockProto.Transaction) request,
              (io.grpc.stub.StreamObserver<com.block.BlockProto.Msg>) responseObserver);
          break;
        case METHODID_NOTICE:
          serviceImpl.notice((com.block.BlockProto.Msg) request,
              (io.grpc.stub.StreamObserver<com.block.BlockProto.Msg>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getResponseLatestBlockMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.protobuf.Empty,
              com.block.BlockProto.Block>(
                service, METHODID_RESPONSE_LATEST_BLOCK)))
        .addMethod(
          getResponseBlockChainMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.protobuf.Empty,
              com.block.BlockProto.BlockChain>(
                service, METHODID_RESPONSE_BLOCK_CHAIN)))
        .addMethod(
          getBroadcastMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.block.BlockProto.Block,
              com.google.protobuf.Empty>(
                service, METHODID_BROADCAST)))
        .addMethod(
          getSendToBoardMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.block.BlockProto.Transaction,
              com.block.BlockProto.Msg>(
                service, METHODID_SEND_TO_BOARD)))
        .addMethod(
          getNoticeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.block.BlockProto.Msg,
              com.block.BlockProto.Msg>(
                service, METHODID_NOTICE)))
        .build();
  }

  private static abstract class BlockServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    BlockServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.block.BlockProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("BlockService");
    }
  }

  private static final class BlockServiceFileDescriptorSupplier
      extends BlockServiceBaseDescriptorSupplier {
    BlockServiceFileDescriptorSupplier() {}
  }

  private static final class BlockServiceMethodDescriptorSupplier
      extends BlockServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    BlockServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (BlockServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new BlockServiceFileDescriptorSupplier())
              .addMethod(getResponseLatestBlockMethod())
              .addMethod(getResponseBlockChainMethod())
              .addMethod(getBroadcastMethod())
              .addMethod(getSendToBoardMethod())
              .addMethod(getNoticeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
