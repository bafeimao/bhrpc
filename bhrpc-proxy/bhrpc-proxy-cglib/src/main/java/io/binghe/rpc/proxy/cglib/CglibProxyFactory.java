package io.binghe.rpc.proxy.cglib;

import io.binghe.rpc.proxy.api.BaseProxyFactory;
import io.binghe.rpc.proxy.api.ProxyFactory;
import io.binghe.rpc.spi.annotation.SPIClass;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author You Chuande
 */
@SPIClass
public class CglibProxyFactory<T> extends BaseProxyFactory<T> implements ProxyFactory {
    private static final Logger log = LoggerFactory.getLogger(CglibProxyFactory.class);
    private final Enhancer enhancer = new Enhancer();

    @Override
    public <T> T getProxy(Class<T> clazz) {
        log.info("基于CGLIB动态代理生成代理对象");
        enhancer.setInterfaces(new Class[]{clazz});
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                return objectProxy.invoke(o, method, objects);
            }
        });
        return (T) enhancer.create();
    }
}
