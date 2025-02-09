package io.binghe.rpc.consumer.common;

import io.binghe.rpc.common.helper.RpcServiceHelper;
import io.binghe.rpc.common.threadpool.ClientThreadPool;
import io.binghe.rpc.consumer.common.handler.RpcConsumerHandler;
import io.binghe.rpc.consumer.common.helper.RpcConsumerHandlerHelper;
import io.binghe.rpc.consumer.common.initializer.RpcConsumerInitializer;
import io.binghe.rpc.protocol.RpcProtocol;
import io.binghe.rpc.protocol.meta.ServiceMeta;
import io.binghe.rpc.protocol.request.RpcRequest;
import io.binghe.rpc.proxy.api.consumer.Consumer;
import io.binghe.rpc.proxy.api.future.RPCFuture;
import io.binghe.rpc.registry.api.RegistryService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author You Chuande
 */
public class RpcConsumer implements Consumer {
    private final Logger log = LoggerFactory.getLogger(RpcConsumer.class);
    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup;

    private static volatile RpcConsumer instance;

    private static Map<String, RpcConsumerHandler> handlerMap = new ConcurrentHashMap<>();

    private RpcConsumer() {
        bootstrap = new Bootstrap();
        eventLoopGroup = new NioEventLoopGroup(4);
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new RpcConsumerInitializer());
    }

    public static RpcConsumer getInstance() {
        if (instance == null) {
            synchronized (RpcConsumer.class) {
                if (instance == null) {
                    instance = new RpcConsumer();
                }
            }
        }
        return instance;
    }

    public void close() {
        RpcConsumerHandlerHelper.closeRpcClientHandler();
        eventLoopGroup.shutdownGracefully();
        ClientThreadPool.shutdown();
    }

    @Override
    public RPCFuture sendRequest(RpcProtocol<RpcRequest> protocol, RegistryService registryService) throws Exception {
        RpcRequest request = protocol.getBody();
        String serviceKey = RpcServiceHelper.buildServiceKey(request.getClassName(), request.getVersion(), request.getGroup());
        Object[] params = request.getParameters();
        int invokeHashCode = (params == null || params.length == 0) ? serviceKey.hashCode() : params[0].hashCode();
        ServiceMeta serviceMeta = registryService.discovery(serviceKey, invokeHashCode);
        if (serviceMeta != null) {
            RpcConsumerHandler handler = RpcConsumerHandlerHelper.get(serviceMeta);
            if (handler == null) {
                handler = getRpcConsumerHandler(serviceMeta.getServiceAddr(), serviceMeta.getServicePort());
                RpcConsumerHandlerHelper.put(serviceMeta, handler);
            } else if (!handler.getChannel().isActive()) {
                handler.close();
                handler = getRpcConsumerHandler(serviceMeta.getServiceAddr(), serviceMeta.getServicePort());
                RpcConsumerHandlerHelper.put(serviceMeta, handler);
            }
            return handler.sendRequest(protocol, request.getAsync(), request.getOneway());
        }
        return null;
    }

    /**
     * 创建链接并返回RpcClientHandler
     */
    private RpcConsumerHandler getRpcConsumerHandler(String serviceAddress, int port) throws InterruptedException {
        ChannelFuture future = bootstrap.connect(serviceAddress, port).sync();
        future.addListener((ChannelFutureListener) listener -> {
            if (future.isSuccess()) {
                log.info("connect rpc server {} on port {} success", serviceAddress, port);
            } else {
                log.error("connect rpc server {} on port {} failed", serviceAddress, port);
                future.cause().printStackTrace();
                eventLoopGroup.shutdownGracefully();
            }
        });
        return future.channel().pipeline().get(RpcConsumerHandler.class);
    }

}
