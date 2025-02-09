package io.binghe.rpc.proxy.jdk;

import io.binghe.rpc.proxy.api.BaseProxyFactory;
import io.binghe.rpc.proxy.api.ProxyFactory;
import io.binghe.rpc.proxy.api.consumer.Consumer;
import io.binghe.rpc.proxy.api.object.ObjectProxy;

import java.lang.reflect.Proxy;

/**
 * @author You Chuande
 */
public class JdkProxyFactory<T> extends BaseProxyFactory<T> implements ProxyFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class<?>[]{clazz},
                objectProxy
        );
    }

}
