package io.binghe.rpc.reflect.javassist;

import io.binghe.rpc.reflect.api.ReflectInvoker;
import io.binghe.rpc.spi.annotation.SPIClass;
import javassist.util.proxy.ProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author You Chuande
 */
@SPIClass
public class JavassistReflectInvoker implements ReflectInvoker {
    private static final Logger log = LoggerFactory.getLogger(JavassistReflectInvoker.class);

    @Override
    public Object invokeMethod(Object serviceBean, Class<?> serivceClass, String methodName, Class<?>[] parameterTypes, Object[] parameters) throws Throwable {
        log.info("基于Javassist的反射调用");
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setSuperclass(serivceClass);
        Class<?> childClass = proxyFactory.createClass();
        Method method = childClass.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(serviceBean, parameters);
    }
}
