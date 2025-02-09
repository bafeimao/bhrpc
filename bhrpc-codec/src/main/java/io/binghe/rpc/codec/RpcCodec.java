package io.binghe.rpc.codec;

import io.binghe.rpc.serialization.api.Serialization;
import io.binghe.rpc.spi.loader.ExtensionLoader;

/**
 * @author You Chuande
 */
public interface RpcCodec {
    default Serialization getJdkSerialization(String serializationType) {
        return ExtensionLoader.getExtension(Serialization.class,serializationType);
    }
}
