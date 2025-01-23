package io.binghe.rpc.serialization.api;

/**
 * @author You Chuande
 */
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
