package io.binghe.rpc.consumer.common.context;


import io.binghe.rpc.proxy.api.future.RPCFuture;

/**
 * @author You Chuande
 */
public class RpcContext {
    public RpcContext() {
    }

    /**
     * RpcContext示例
     */
    private static final RpcContext AGENT = new RpcContext();

    /**
     * 存放RpcFuture的InheritableThreadLocal
     */
    private static final InheritableThreadLocal<RPCFuture>
            RPC_FUTURE_INHERITABLE_THREAD_LOCAL = new InheritableThreadLocal<>();

    /**
     * 获取上下文
     *
     * @return RPC服务的上下文信息
     */
    public static RpcContext getContext() {
        return AGENT;
    }

    /**
     * 将RpcFuture 放入上下文
     *
     * @param rpcFuture RpcFuture
     */
    public void setRPCFuture(RPCFuture rpcFuture) {
        RPC_FUTURE_INHERITABLE_THREAD_LOCAL.set(rpcFuture);
    }

    /**
     * 获取RpcFuture
     *
     * @return RpcFuture
     */
    public RPCFuture getRPCFuture() {
        return RPC_FUTURE_INHERITABLE_THREAD_LOCAL.get();
    }

    /**
     * 清除RpcFuture
     */
    public void removeRPCFuture() {
        RPC_FUTURE_INHERITABLE_THREAD_LOCAL.remove();
    }
}
