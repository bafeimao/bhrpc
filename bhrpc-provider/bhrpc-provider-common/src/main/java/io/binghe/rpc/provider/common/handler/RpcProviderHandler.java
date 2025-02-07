package io.binghe.rpc.provider.common.handler;

import io.binghe.rpc.common.helper.RpcServiceHelper;
import io.binghe.rpc.common.threadpool.ServerThreadPool;
import io.binghe.rpc.protocol.RpcProtocol;
import io.binghe.rpc.protocol.enumeration.RpcStatus;
import io.binghe.rpc.protocol.enumeration.RpcType;
import io.binghe.rpc.protocol.header.RpcHeader;
import io.binghe.rpc.protocol.request.RpcRequest;
import io.binghe.rpc.protocol.response.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
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
        ServerThreadPool.submit(() -> {
            RpcHeader header = protocol.getHeader();
            header.setMsgType((byte) RpcType.RESPONSE.getType());
            RpcRequest request = protocol.getBody();
            RpcProtocol<RpcResponse> responseProtocol = new RpcProtocol<>();
            RpcResponse response = new RpcResponse();
            try {
                Object result = this.handle(request);
                response.setResult(result);
                response.setAsync(request.getAsync());
                response.setOneway(request.getOneway());
                header.setStatus((byte) RpcStatus.SUCCESS.getCode());
            } catch (Throwable e) {
                response.setError(e.toString());
                header.setStatus((byte) RpcStatus.FAIL.getCode());
            }
            responseProtocol.setHeader(header);
            responseProtocol.setBody(response);
            channelHandlerContext.writeAndFlush(responseProtocol);
        });
    }

    private Object handle(RpcRequest request) throws Throwable {
        String serviceKey = RpcServiceHelper.buildServiceKey(request.getClassName(), request.getVersion(), request.getGroup());
        Object serviceBean = handlerMap.get(serviceKey);
        if (serviceBean == null) {
            throw new RuntimeException(String.format("service not exist: %s", serviceKey));
        }
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();
        return this.invokeMethod(serviceBean, serviceClass, methodName, parameterTypes, parameters);
    }

    private Object invokeMethod(Object serviceBean, Class<?> serviceClass, String methodName, Class<?>[] parameterType, Object[] parameters) throws Exception {
        Method method = serviceClass.getMethod(methodName, parameterType);
        if (method != null) {
            method.setAccessible(true);
            return method.invoke(serviceBean, parameters);
        }
        return null;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("server caught exception", cause);
        ctx.close();
    }
}
