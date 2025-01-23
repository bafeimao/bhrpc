package io.binghe.rpc.protocol.enumeration;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author You Chuande
 */
public enum RpcType {
    //请求消息
    REQUEST(1),
    //响应消息
    RESPONSE(2),
    //心跳消息
    HEARTBEAT(3);

    private final int type;

    RpcType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static RpcType findByType(int type) {
        Optional<RpcType> typeOpt = Arrays.stream(RpcType.values())
                .filter(rpcType -> rpcType.getType() == type)
                .findFirst();
        return typeOpt.orElse(null);
    }
}
