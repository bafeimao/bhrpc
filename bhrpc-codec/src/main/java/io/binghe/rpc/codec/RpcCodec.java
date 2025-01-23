package io.binghe.rpc.codec;

import io.binghe.rpc.serialization.api.Serialization;
import io.binghe.rpc.serialization.jdk.JdkSerialization;

/**
 * @author You Chuande
 */
public interface RpcCodec {
    default Serialization getJdkSerialization() {
        return new JdkSerialization();
    }
}
