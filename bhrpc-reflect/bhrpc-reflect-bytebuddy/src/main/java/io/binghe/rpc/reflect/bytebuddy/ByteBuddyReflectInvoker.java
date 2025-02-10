package io.binghe.rpc.reflect.bytebuddy;

import io.binghe.rpc.reflect.api.ReflectInvoker;
import io.binghe.rpc.spi.annotation.SPIClass;
import net.bytebuddy.ByteBuddy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author You Chuande
 */
@SPIClass
public class ByteBuddyReflectInvoker implements ReflectInvoker {
    private static final Logger log = LoggerFactory.getLogger(ByteBuddyReflectInvoker.class);

    @Override
    public Object invokeMethod(Object serviceBean, Class<?> serivceClass, String methodName, Class<?>[] parameterTypes, Object[] parameters) throws Throwable {
        log.info("基于ByteBuddy的反射调用");
        Class<?> childClass = new ByteBuddy().subclass(serivceClass)
                .make()
                .load(ByteBuddyReflectInvoker.class.getClassLoader())
                .getLoaded();
        Object instance = childClass.getDeclaredConstructor().newInstance();
        Method method = childClass.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(instance, parameters);
    }
}
