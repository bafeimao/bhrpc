package io.binghe.rpc.protocol.enumeration;

/**
 * @author You Chuande
 */
public enum RpcStatus {
    SUCCESS(0),
    FAIL(1);

    private final int code;

    RpcStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
