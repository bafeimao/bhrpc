package io.binghe.rpc.proxy.api;

import io.binghe.rpc.proxy.api.config.ProxyConfig;
import io.binghe.rpc.proxy.api.object.ObjectProxy;

/**
 * @author You Chuande
 */
public abstract class BaseProxyFactory<T> implements ProxyFactory{

    protected ObjectProxy<T> objectProxy;

    @Override
    public <T> void init(ProxyConfig<T> proxyConfig) {
        this.objectProxy = new ObjectProxy(proxyConfig.getClazz(),
                proxyConfig.getServiceVersion(),
                proxyConfig.getServiceGroup(),
                proxyConfig.getSerializationType(),
                proxyConfig.getTimeout(),
                proxyConfig.getConsumer(),
                proxyConfig.getAsync(),
                proxyConfig.getOneway());
    }
}
