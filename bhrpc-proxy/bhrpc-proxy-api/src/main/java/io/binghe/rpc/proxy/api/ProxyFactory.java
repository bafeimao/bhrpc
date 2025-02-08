package io.binghe.rpc.proxy.api;

import io.binghe.rpc.proxy.api.config.ProxyConfig;

/**
 * @author You Chuande
 */
public interface ProxyFactory {
    /**
     * 获取代理对象
     */
    <T> T getProxy(Class<T> clazz);

    /**
     * 默认初始化方法
     */
    default <T> void init(ProxyConfig<T> proxyConfig) {
    }
}
