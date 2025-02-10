package io.binghe.rpc.reflect.jdk;

import io.binghe.rpc.reflect.api.ReflectInvoker;
import io.binghe.rpc.spi.annotation.SPIClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author You Chuande
 */
@SPIClass
public class JdkReflectInvoker implements ReflectInvoker {
    private static final Logger log = LoggerFactory.getLogger(JdkReflectInvoker.class);

    @Override
    public Object invokeMethod(Object serviceBean, Class<?> serivceClass, String methodName, Class<?>[] parameterTypes, Object[] parameters) throws Throwable {
        log.info("基于JDK的反射调用");
        Method method = serivceClass.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(serviceBean, parameters);
    }
}
