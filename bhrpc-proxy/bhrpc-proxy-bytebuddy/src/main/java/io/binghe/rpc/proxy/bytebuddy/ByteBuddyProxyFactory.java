package io.binghe.rpc.proxy.bytebuddy;

import io.binghe.rpc.proxy.api.BaseProxyFactory;
import io.binghe.rpc.proxy.api.ProxyFactory;
import io.binghe.rpc.spi.annotation.SPIClass;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;

/**
 * @author You Chuande
 */
@SPIClass
public class ByteBuddyProxyFactory<T> extends BaseProxyFactory<T> implements ProxyFactory {

    private static final Logger log = LoggerFactory.getLogger(ByteBuddyProxyFactory.class);

    @Override
    public <T> T getProxy(Class<T> clazz) {
        try {
            log.info("基于ByteBuddy的代理");
            return (T) new ByteBuddy().subclass(Object.class)
                    .implement(clazz)
                    .intercept(InvocationHandlerAdapter.of(objectProxy))
                    .make()
                    .load(ByteBuddyProxyFactory.class.getClassLoader())
                    .getLoaded()
                    .getDeclaredConstructor()
                    .newInstance();
        } catch (Exception e) {
            log.error("ByteBuddy代理异常", e);
        }
        return null;
    }
}
