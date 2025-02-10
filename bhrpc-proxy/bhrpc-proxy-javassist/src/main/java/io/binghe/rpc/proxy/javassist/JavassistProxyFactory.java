package io.binghe.rpc.proxy.javassist;

import io.binghe.rpc.proxy.api.BaseProxyFactory;
import io.binghe.rpc.proxy.api.ProxyFactory;
import io.binghe.rpc.spi.annotation.SPIClass;
import javassist.util.proxy.MethodHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author You Chuande
 */
@SPIClass
public class JavassistProxyFactory<T> extends BaseProxyFactory<T> implements ProxyFactory {

    private static final Logger log = LoggerFactory.getLogger(JavassistProxyFactory.class);
    private javassist.util.proxy.ProxyFactory proxyFactory = new javassist.util.proxy.ProxyFactory();

    @Override
    public <T> T getProxy(Class<T> clazz) {
        log.info("基于Javassist的代理");
        try {
            proxyFactory.setInterfaces(new Class[]{clazz});
            proxyFactory.setHandler(new MethodHandler() {
                @Override
                public Object invoke(Object o, Method method, Method method1, Object[] objects) throws Throwable {
                    return objectProxy.invoke(clazz, method, objects);
                }
            });
            return (T) proxyFactory.createClass().newInstance();
        } catch (Exception e) {
            log.error("Javassist代理异常", e);
        }
        return null;
    }
}
