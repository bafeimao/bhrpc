package io.binghe.rpc.protocol.base;

import java.io.Serializable;

/**
 * @author You Chuande
 */
public class RpcMessage implements Serializable {
    /**
     * 是否单向发送
     */
    private Boolean oneway;

    /**
     * 是否异步调用
     */
    private Boolean async;


    public Boolean getOneway() {
        return oneway;
    }

    public RpcMessage setOneway(Boolean oneway) {
        this.oneway = oneway;
        return this;
    }

    public Boolean getAsync() {
        return async;
    }

    public RpcMessage setAsync(Boolean async) {
        this.async = async;
        return this;
    }
}
