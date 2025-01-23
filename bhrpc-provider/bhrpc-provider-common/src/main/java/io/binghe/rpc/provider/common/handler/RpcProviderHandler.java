package io.binghe.rpc.provider.common.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author You Chuande
 */
public class RpcProviderHandler extends SimpleChannelInboundHandler<Object> {
    private final Logger log = LoggerFactory.getLogger(RpcProviderHandler.class);
    private final Map<String, Object> handlerMap;

    public RpcProviderHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        log.info("RPC服务提供者接收到的数据====>>>>{}", o.toString());
        log.info("handlerMap存放的数据如下:");
        handlerMap.forEach((k, v) -> {
            log.info("key:{},value:{}", k, v);
        });
        channelHandlerContext.writeAndFlush(o);
    }

}
