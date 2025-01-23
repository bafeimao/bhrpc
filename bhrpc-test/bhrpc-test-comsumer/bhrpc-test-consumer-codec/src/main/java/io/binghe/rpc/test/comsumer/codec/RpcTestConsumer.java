package io.binghe.rpc.test.comsumer.codec;

import io.binghe.rpc.test.comsumer.codec.init.RpcTestConsumerInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;

/**
 * @author You Chuande
 */
public class RpcTestConsumer {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RpcTestConsumer.class);

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup eventExecutors = new NioEventLoopGroup(4);
        try {
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new RpcTestConsumerInitializer());
            bootstrap.connect("127.0.0.1", 27880).sync();
        } catch (Exception e) {
            log.error("服务消费者异常===》》》", e);
        } finally {
            Thread.sleep(2000);
            eventExecutors.shutdownGracefully();
        }

    }
}
