package io.binghe.rpc.protocol.header;

import io.binghe.rpc.common.id.IdFactory;
import io.binghe.rpc.constants.RpcConstants;
import io.binghe.rpc.protocol.enumeration.RpcType;

/**
 * @author You Chuande
 */
public class RpcHeaderFactory {
    public static RpcHeader getRequestHeader(String serializationType) {
        return new RpcHeader()
                .setRequestId(IdFactory.getId())
                .setMagic(RpcConstants.MAGIC)
                .setMsgType((byte) RpcType.REQUEST.getType())
                .setStatus((byte) 0x1)
                .setSerializationType(serializationType);
    }
}
