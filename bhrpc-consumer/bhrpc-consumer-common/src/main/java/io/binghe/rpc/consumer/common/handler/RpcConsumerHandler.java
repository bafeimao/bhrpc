package io.binghe.rpc.consumer.common.handler;

import com.alibaba.fastjson2.JSONObject;
import io.binghe.rpc.protocol.RpcProtocol;
import io.binghe.rpc.protocol.header.RpcHeader;
import io.binghe.rpc.protocol.request.RpcRequest;
import io.binghe.rpc.protocol.response.RpcResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author You Chuande
 */
public class RpcConsumerHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcResponse>> {

    private final Logger log = LoggerFactory.getLogger(RpcConsumerHandler.class);
    private volatile Channel channel;
    private SocketAddress remotePeer;
    //存储请求ID与RpcResponse协议的映射关系
    private Map<Long, RpcProtocol<RpcResponse>> pendingResponse = new ConcurrentHashMap<>();

    public Channel getChannel() {
        return channel;
    }

    public SocketAddress getRemotePeer() {
        return remotePeer;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.remotePeer = this.channel.remoteAddress();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcResponse> msg) throws Exception {
        if (msg == null){
            return;
        }
        log.info("服务消费者接收到的数据===>>>{}", JSONObject.toJSONString(msg));
        RpcHeader header = msg.getHeader();
        long requestId = header.getRequestId();
        pendingResponse.put(requestId, msg);
    }

    /**
     * 服务消费者向服务提供者发送数据
     */
    public Object sendRequest(RpcProtocol<RpcRequest> protocol) {
        log.info("服务消费者发送的数据===>>>{}", JSONObject.toJSONString(protocol));
        channel.writeAndFlush(protocol);
        RpcHeader header = protocol.getHeader();
        long requestId = header.getRequestId();
        while (true) {
            RpcProtocol<RpcResponse> responseRpcProtocol = pendingResponse.remove(requestId);
            if (responseRpcProtocol != null) {
                return responseRpcProtocol.getBody().getResult();
            }
        }
    }

    public void close() {
        channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }
}
