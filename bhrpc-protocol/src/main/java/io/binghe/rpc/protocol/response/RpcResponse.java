package io.binghe.rpc.protocol.response;

import io.binghe.rpc.protocol.base.RpcMessage;

/**
 * @author You Chuande
 */
public class RpcResponse extends RpcMessage {

    private static final long serialVersionUID = 1L;

    private String error;

    private Object result;

    public String getError() {
        return error;
    }

    public RpcResponse setError(String error) {
        this.error = error;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public RpcResponse setResult(Object result) {
        this.result = result;
        return this;
    }
}
