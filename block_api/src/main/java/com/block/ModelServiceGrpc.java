package com.block;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.63.0)",
    comments = "Source: model.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ModelServiceGrpc {

  private ModelServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "model_service.ModelService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.block.ModelProto.ModelState,
      com.block.ModelProto.Msg> getLatestModelMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "latestModel",
      requestType = com.block.ModelProto.ModelState.class,
      responseType = com.block.ModelProto.Msg.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.block.ModelProto.ModelState,
      com.block.ModelProto.Msg> getLatestModelMethod() {
    io.grpc.MethodDescriptor<com.block.ModelProto.ModelState, com.block.ModelProto.Msg> getLatestModelMethod;
    if ((getLatestModelMethod = ModelServiceGrpc.getLatestModelMethod) == null) {
      synchronized (ModelServiceGrpc.class) {
        if ((getLatestModelMethod = ModelServiceGrpc.getLatestModelMethod) == null) {
          ModelServiceGrpc.getLatestModelMethod = getLatestModelMethod =
              io.grpc.MethodDescriptor.<com.block.ModelProto.ModelState, com.block.ModelProto.Msg>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "latestModel"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.block.ModelProto.ModelState.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.block.ModelProto.Msg.getDefaultInstance()))
              .setSchemaDescriptor(new ModelServiceMethodDescriptorSupplier("latestModel"))
              .build();
        }
      }
    }
    return getLatestModelMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.block.ModelProto.ModelState,
      com.block.ModelProto.Accuracy> getTestModelMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "testModel",
      requestType = com.block.ModelProto.ModelState.class,
      responseType = com.block.ModelProto.Accuracy.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.block.ModelProto.ModelState,
      com.block.ModelProto.Accuracy> getTestModelMethod() {
    io.grpc.MethodDescriptor<com.block.ModelProto.ModelState, com.block.ModelProto.Accuracy> getTestModelMethod;
    if ((getTestModelMethod = ModelServiceGrpc.getTestModelMethod) == null) {
      synchronized (ModelServiceGrpc.class) {
        if ((getTestModelMethod = ModelServiceGrpc.getTestModelMethod) == null) {
          ModelServiceGrpc.getTestModelMethod = getTestModelMethod =
              io.grpc.MethodDescriptor.<com.block.ModelProto.ModelState, com.block.ModelProto.Accuracy>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "testModel"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.block.ModelProto.ModelState.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.block.ModelProto.Accuracy.getDefaultInstance()))
              .setSchemaDescriptor(new ModelServiceMethodDescriptorSupplier("testModel"))
              .build();
        }
      }
    }
    return getTestModelMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.block.ModelProto.Msg,
      com.block.ModelProto.Msg> getNoticeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "notice",
      requestType = com.block.ModelProto.Msg.class,
      responseType = com.block.ModelProto.Msg.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.block.ModelProto.Msg,
      com.block.ModelProto.Msg> getNoticeMethod() {
    io.grpc.MethodDescriptor<com.block.ModelProto.Msg, com.block.ModelProto.Msg> getNoticeMethod;
    if ((getNoticeMethod = ModelServiceGrpc.getNoticeMethod) == null) {
      synchronized (ModelServiceGrpc.class) {
        if ((getNoticeMethod = ModelServiceGrpc.getNoticeMethod) == null) {
          ModelServiceGrpc.getNoticeMethod = getNoticeMethod =
              io.grpc.MethodDescriptor.<com.block.ModelProto.Msg, com.block.ModelProto.Msg>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "notice"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.block.ModelProto.Msg.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.block.ModelProto.Msg.getDefaultInstance()))
              .setSchemaDescriptor(new ModelServiceMethodDescriptorSupplier("notice"))
              .build();
        }
      }
    }
    return getNoticeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ModelServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ModelServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ModelServiceStub>() {
        @java.lang.Override
        public ModelServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ModelServiceStub(channel, callOptions);
        }
      };
    return ModelServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ModelServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ModelServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ModelServiceBlockingStub>() {
        @java.lang.Override
        public ModelServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ModelServiceBlockingStub(channel, callOptions);
        }
      };
    return ModelServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ModelServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ModelServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ModelServiceFutureStub>() {
        @java.lang.Override
        public ModelServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ModelServiceFutureStub(channel, callOptions);
        }
      };
    return ModelServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void latestModel(com.block.ModelProto.ModelState request,
        io.grpc.stub.StreamObserver<com.block.ModelProto.Msg> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLatestModelMethod(), responseObserver);
    }

    /**
     */
    default void testModel(com.block.ModelProto.ModelState request,
        io.grpc.stub.StreamObserver<com.block.ModelProto.Accuracy> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getTestModelMethod(), responseObserver);
    }

    /**
     */
    default void notice(com.block.ModelProto.Msg request,
        io.grpc.stub.StreamObserver<com.block.ModelProto.Msg> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getNoticeMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ModelService.
   */
  public static abstract class ModelServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ModelServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ModelService.
   */
  public static final class ModelServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ModelServiceStub> {
    private ModelServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ModelServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ModelServiceStub(channel, callOptions);
    }

    /**
     */
    public void latestModel(com.block.ModelProto.ModelState request,
        io.grpc.stub.StreamObserver<com.block.ModelProto.Msg> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLatestModelMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void testModel(com.block.ModelProto.ModelState request,
        io.grpc.stub.StreamObserver<com.block.ModelProto.Accuracy> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getTestModelMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void notice(com.block.ModelProto.Msg request,
        io.grpc.stub.StreamObserver<com.block.ModelProto.Msg> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getNoticeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ModelService.
   */
  public static final class ModelServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ModelServiceBlockingStub> {
    private ModelServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ModelServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ModelServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.block.ModelProto.Msg latestModel(com.block.ModelProto.ModelState request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLatestModelMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.block.ModelProto.Accuracy testModel(com.block.ModelProto.ModelState request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getTestModelMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.block.ModelProto.Msg notice(com.block.ModelProto.Msg request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getNoticeMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ModelService.
   */
  public static final class ModelServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ModelServiceFutureStub> {
    private ModelServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ModelServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ModelServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.block.ModelProto.Msg> latestModel(
        com.block.ModelProto.ModelState request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLatestModelMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.block.ModelProto.Accuracy> testModel(
        com.block.ModelProto.ModelState request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getTestModelMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.block.ModelProto.Msg> notice(
        com.block.ModelProto.Msg request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getNoticeMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LATEST_MODEL = 0;
  private static final int METHODID_TEST_MODEL = 1;
  private static final int METHODID_NOTICE = 2;

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
        case METHODID_LATEST_MODEL:
          serviceImpl.latestModel((com.block.ModelProto.ModelState) request,
              (io.grpc.stub.StreamObserver<com.block.ModelProto.Msg>) responseObserver);
          break;
        case METHODID_TEST_MODEL:
          serviceImpl.testModel((com.block.ModelProto.ModelState) request,
              (io.grpc.stub.StreamObserver<com.block.ModelProto.Accuracy>) responseObserver);
          break;
        case METHODID_NOTICE:
          serviceImpl.notice((com.block.ModelProto.Msg) request,
              (io.grpc.stub.StreamObserver<com.block.ModelProto.Msg>) responseObserver);
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
          getLatestModelMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.block.ModelProto.ModelState,
              com.block.ModelProto.Msg>(
                service, METHODID_LATEST_MODEL)))
        .addMethod(
          getTestModelMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.block.ModelProto.ModelState,
              com.block.ModelProto.Accuracy>(
                service, METHODID_TEST_MODEL)))
        .addMethod(
          getNoticeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.block.ModelProto.Msg,
              com.block.ModelProto.Msg>(
                service, METHODID_NOTICE)))
        .build();
  }

  private static abstract class ModelServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ModelServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.block.ModelProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ModelService");
    }
  }

  private static final class ModelServiceFileDescriptorSupplier
      extends ModelServiceBaseDescriptorSupplier {
    ModelServiceFileDescriptorSupplier() {}
  }

  private static final class ModelServiceMethodDescriptorSupplier
      extends ModelServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ModelServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (ModelServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ModelServiceFileDescriptorSupplier())
              .addMethod(getLatestModelMethod())
              .addMethod(getTestModelMethod())
              .addMethod(getNoticeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
