package io.binghe.rpc.reflect.api;

import io.binghe.rpc.spi.annotation.SPI;

/**
 * @author You Chuande
 */
@SPI
public interface ReflectInvoker {

    /**
     * 调用真实方法的SPI通用接口
     * @param serviceBean 方法所在的对象实例
     * @param serivceClass 方法所在对象的实例的Class对象
     * @param methodName 方法名
     * @param parameterTypes 方法参数类型数组
     * @param parameters 方法参数数组
     * @return 方法调用的结果信息
     * @throws Throwable 方法调用过程中的异常
     */
    Object invokeMethod(Object serviceBean, Class<?> serivceClass, String methodName,
                        Class<?>[] parameterTypes, Object[] parameters) throws Throwable;

}
