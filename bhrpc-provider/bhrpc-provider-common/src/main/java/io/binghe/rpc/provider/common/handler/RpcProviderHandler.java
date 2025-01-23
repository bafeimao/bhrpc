package io.binghe.rpc.provider.common.handler;

import com.alibaba.fastjson2.JSONObject;
import io.binghe.rpc.protocol.RpcProtocol;
import io.binghe.rpc.protocol.enumeration.RpcType;
import io.binghe.rpc.protocol.header.RpcHeader;
import io.binghe.rpc.protocol.request.RpcRequest;
import io.binghe.rpc.protocol.response.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author You Chuande
 */
public class RpcProviderHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcRequest>> {
    private final Logger log = LoggerFactory.getLogger(RpcProviderHandler.class);
    private final Map<String, Object> handlerMap;

    public RpcProviderHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocol<RpcRequest> protocol) throws Exception {
        log.info("RPC服务提供者接收到的数据====>>>>{}", JSONObject.toJSONString(protocol));
        log.info("handlerMap存放的数据如下:");
        handlerMap.forEach((k, v) -> {
            log.info("key:{},value:{}", k, v);
        });
        RpcHeader header = protocol.getHeader();
        RpcRequest request = protocol.getBody();
        header.setMsgType((byte) RpcType.RESPONSE.getType());
        RpcProtocol<RpcResponse> responseRpcProtocol = new RpcProtocol<>();
        RpcResponse response = new RpcResponse();
        response.setResult("数据交互成功");
        response.setAsync(request.getAsync());
        response.setOneway(request.getOneway());
        responseRpcProtocol.setHeader(header);
        responseRpcProtocol.setBody(response);
        channelHandlerContext.writeAndFlush(responseRpcProtocol);
    }

}
