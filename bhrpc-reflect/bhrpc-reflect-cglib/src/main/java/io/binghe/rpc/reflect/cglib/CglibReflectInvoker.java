package io.binghe.rpc.reflect.cglib;

import io.binghe.rpc.reflect.api.ReflectInvoker;
import io.binghe.rpc.spi.annotation.SPIClass;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author You Chuande
 */
@SPIClass
public class CglibReflectInvoker implements ReflectInvoker {
    private static final Logger log = LoggerFactory.getLogger(CglibReflectInvoker.class);

    @Override
    public Object invokeMethod(Object serviceBean, Class<?> serivceClass, String methodName, Class<?>[] parameterTypes, Object[] parameters) throws Throwable {
        log.info("基于Cglib的反射调用");
        FastClass fastClass = FastClass.create(serivceClass);
        FastMethod fastMethod = fastClass.getMethod(methodName, parameterTypes);
        return fastMethod.invoke(serviceBean, parameters);
    }
}
