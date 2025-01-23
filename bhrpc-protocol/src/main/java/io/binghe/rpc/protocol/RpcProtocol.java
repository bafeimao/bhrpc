package io.binghe.rpc.protocol;

import io.binghe.rpc.protocol.header.RpcHeader;

import java.io.Serializable;

/**
 * @author You Chuande
 */
public class RpcProtocol<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 消息头
     */
    private RpcHeader header;
    /**
     * 消息体
     */
    private T body;

    public RpcHeader getHeader() {
        return header;
    }

    public RpcProtocol<T> setHeader(RpcHeader header) {
        this.header = header;
        return this;
    }

    public T getBody() {
        return body;
    }

    public RpcProtocol<T> setBody(T body) {
        this.body = body;
        return this;
    }
}
