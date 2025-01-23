package io.binghe.rpc.protocol.base;

import java.io.Serializable;

/**
 * @author You Chuande
 */
public class RpcMessage implements Serializable {
    /**
     * 是否单向发送
     */
    private boolean oneway;

    /**
     * 是否异步调用
     */
    private boolean async;

    public boolean isOneway() {
        return oneway;
    }

    public RpcMessage setOneway(boolean oneway) {
        this.oneway = oneway;
        return this;
    }

    public boolean isAsync() {
        return async;
    }

    public RpcMessage setAsync(boolean async) {
        this.async = async;
        return this;
    }
}
