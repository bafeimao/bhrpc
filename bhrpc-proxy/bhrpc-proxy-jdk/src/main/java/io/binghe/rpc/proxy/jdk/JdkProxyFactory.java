package io.binghe.rpc.proxy.jdk;

import io.binghe.rpc.proxy.api.BaseProxyFactory;
import io.binghe.rpc.proxy.api.ProxyFactory;
import io.binghe.rpc.proxy.api.consumer.Consumer;
import io.binghe.rpc.proxy.api.object.ObjectProxy;
import io.binghe.rpc.spi.annotation.SPIClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;

/**
 * @author You Chuande
 */
@SPIClass
public class JdkProxyFactory<T> extends BaseProxyFactory<T> implements ProxyFactory {

    private static final Logger log = LoggerFactory.getLogger(JdkProxyFactory.class);

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getProxy(Class<T> clazz) {
        log.info("基于JDK动态代理生成代理对象");
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class<?>[]{clazz},
                objectProxy
        );
    }

}
