package io.binghe.rpc.serialization.protostuff;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import io.binghe.rpc.common.exception.SerializerException;
import io.binghe.rpc.serialization.api.Serialization;
import io.binghe.rpc.spi.annotation.SPIClass;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author You Chuande
 */
@SPIClass
public class ProtostuffSerialization implements Serialization {
    private static final Logger log = LoggerFactory.getLogger(ProtostuffSerialization.class);

    private Map<Class<?>, Schema<?>> schemaCache = new ConcurrentHashMap<>();

    private Objenesis objenesis = new ObjenesisStd(true);

    private <T> Schema<T> getSchema(Class<T> cls) {
        Schema<T> schema = (Schema<T>) schemaCache.get(cls);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls);
            schemaCache.put(cls, schema);
        }
        return schema;
    }

    @Override
    public <T> byte[] serialize(T obj) {
        log.info("protostuff serialize");
        if (obj == null) {
            throw new SerializerException("serialize object is null");
        }
        Class<T> cls = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = getSchema(cls);
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new SerializerException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> cls) {
        log.info("protostuff deserialize");
        if (data == null) {
            throw new SerializerException("deserialize data is null");
        }
        try {
            T message = objenesis.newInstance(cls);
            Schema<T> schema = getSchema(cls);
            ProtostuffIOUtil.mergeFrom(data, message, schema);
            return message;
        } catch (Exception e) {
            throw new SerializerException(e.getMessage(), e);
        }
    }
}
