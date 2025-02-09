package io.binghe.rpc.serialization.api;

import io.binghe.rpc.constants.RpcConstants;
import io.binghe.rpc.spi.annotation.SPI;

/**
 * @author You Chuande
 */
@SPI(RpcConstants.SERIALIZATION_JDK)
public interface Serialization {
    /**
     * 序列化
     */
    <T> byte[] serialize(T obj);

    /**
     * 反序列化
     */
    <T> T deserialize(byte[] data, Class<T> cls);
}
