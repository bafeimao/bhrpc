package io.binghe.rpc.provider.common.server.base;

import io.binghe.rpc.codec.RpcDecoder;
import io.binghe.rpc.codec.RpcEncoder;
import io.binghe.rpc.provider.common.handler.RpcProviderHandler;
import io.binghe.rpc.provider.common.server.api.Server;
import io.binghe.rpc.registry.api.RegistryService;
import io.binghe.rpc.registry.api.config.RegistryConfig;
import io.binghe.rpc.registry.zookeeper.ZookeeperRegistryService;
import io.binghe.rpc.spi.loader.ExtensionLoader;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author You Chuande
 */
public class BaseServer implements Server {

    private final Logger log = LoggerFactory.getLogger(BaseServer.class);
    //服务提供者监听的主机名:ip/域名
    protected String host = "127.0.0.1";
    //服务提供者监听的端口
    protected int port = 27110;
    //存储实体类的关系
    protected Map<String, Object> handlerMap = new HashMap<>();

    private String reflectType;

    protected RegistryService registryService;

    public BaseServer(String serverAddress, String registryAddress, String registryType,String registryLoadBalanceType, String reflectType) {
        if (!StringUtils.isEmpty(serverAddress)) {
            String[] array = serverAddress.split(":");
            host = array[0];
            port = Integer.parseInt(array[1]);
        }
        this.reflectType = reflectType;
        this.registryService = getRegistryService(registryAddress, registryType, registryLoadBalanceType);
    }

    private RegistryService getRegistryService(String registryAddress, String registryType, String registryLoadBalanceType) {
        RegistryService registryService = null;
        try {
            registryService = ExtensionLoader.getExtension(RegistryService.class,registryType);
            registryService.init(new RegistryConfig(registryAddress, registryType,registryLoadBalanceType));
        } catch (Exception e) {
            log.error("RPC Server error", e);
        }
        return registryService;
    }

    @Override
    public void startNettyServer() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //todo 预留编解码，需要实现自定义协议
                            socketChannel
                                    .pipeline()
                                    .addLast(new RpcDecoder())
                                    .addLast(new RpcEncoder())
                                    .addLast(new RpcProviderHandler(reflectType, handlerMap));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = serverBootstrap.bind(host, port).sync();
            log.info("server started on {}:{}", host, port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("Rpc server start error", e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
