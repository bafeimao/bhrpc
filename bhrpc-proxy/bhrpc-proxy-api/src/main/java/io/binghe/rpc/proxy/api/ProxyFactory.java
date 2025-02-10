package io.binghe.rpc.proxy.api;

import io.binghe.rpc.proxy.api.config.ProxyConfig;
import io.binghe.rpc.spi.annotation.SPI;

/**
 * @author You Chuande
 */
@SPI
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
