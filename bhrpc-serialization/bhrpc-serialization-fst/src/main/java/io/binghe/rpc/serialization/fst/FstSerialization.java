package io.binghe.rpc.serialization.fst;

import io.binghe.rpc.common.exception.SerializerException;
import io.binghe.rpc.serialization.api.Serialization;
import io.binghe.rpc.spi.annotation.SPIClass;
import org.nustaq.serialization.FSTConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author You Chuande
 */
@SPIClass
public class FstSerialization implements Serialization {
    private static final Logger log = LoggerFactory.getLogger(FstSerialization.class);

    @Override
    public <T> byte[] serialize(T obj) {
        log.info("fst serialize");
        if (obj == null) {
            throw new SerializerException("serialize object is null");
        }
        FSTConfiguration fstConfiguration = FSTConfiguration.getDefaultConfiguration();
        return fstConfiguration.asByteArray(obj);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> cls) {
        log.info("fst deserialize");
        if (data == null) {
            throw new SerializerException("deserialize data is null");
        }
        FSTConfiguration fstConfiguration = FSTConfiguration.getDefaultConfiguration();
        return (T) fstConfiguration.asObject(data);
    }
}
