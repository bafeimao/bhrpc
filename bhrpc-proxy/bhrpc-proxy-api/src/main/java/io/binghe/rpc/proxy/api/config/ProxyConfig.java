package io.binghe.rpc.proxy.api.config;

import io.binghe.rpc.proxy.api.consumer.Consumer;

import java.io.Serializable;

/**
 * @author You Chuande
 */
public class ProxyConfig<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 接口的class实例
     */
    private Class<T> clazz;

    /**
     * 服务版本号
     */
    private String serviceVersion;

    /**
     * 服务分组
     */
    private String serviceGroup;

    /**
     * 序列化类型
     */
    private String serializationType;

    /**
     * 超时时间
     */
    private long timeout;

    /**
     * 消费者接口
     */
    private Consumer consumer;

    /**
     * 是否异步调用
     */
    private boolean async;

    /**
     * 是否单向调用
     */
    private boolean oneway;

    public ProxyConfig(Class<T> clazz, String serviceVersion, String serviceGroup, String serializationType, long timeout, Consumer consumer, boolean async, boolean oneway) {
        this.clazz = clazz;
        this.serviceVersion = serviceVersion;
        this.serviceGroup = serviceGroup;
        this.serializationType = serializationType;
        this.timeout = timeout;
        this.consumer = consumer;
        this.async = async;
        this.oneway = oneway;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public ProxyConfig<T> setClazz(Class<T> clazz) {
        this.clazz = clazz;
        return this;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public ProxyConfig<T> setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
        return this;
    }

    public String getServiceGroup() {
        return serviceGroup;
    }

    public ProxyConfig<T> setServiceGroup(String serviceGroup) {
        this.serviceGroup = serviceGroup;
        return this;
    }

    public String getSerializationType() {
        return serializationType;
    }

    public ProxyConfig<T> setSerializationType(String serializationType) {
        this.serializationType = serializationType;
        return this;
    }

    public long getTimeout() {
        return timeout;
    }

    public ProxyConfig<T> setTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public ProxyConfig<T> setConsumer(Consumer consumer) {
        this.consumer = consumer;
        return this;
    }

    public boolean getAsync() {
        return async;
    }

    public ProxyConfig<T> setAsync(boolean async) {
        this.async = async;
        return this;
    }

    public boolean getOneway() {
        return oneway;
    }

    public ProxyConfig<T> setOneway(boolean oneway) {
        this.oneway = oneway;
        return this;
    }
}
