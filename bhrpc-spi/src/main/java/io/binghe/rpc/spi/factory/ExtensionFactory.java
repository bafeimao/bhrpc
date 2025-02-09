package io.binghe.rpc.spi.factory;

import io.binghe.rpc.spi.annotation.SPI;

/**
 * @author You Chuande
 */
@SPI("spi")
public interface ExtensionFactory {

    /**
     * 获取扩展类对象
     * @param key 传入的key值
     * @param clazz 传入的类
     * @return 返回的对象
     * @param <T> 泛型
     */
    <T> T getExtension(String key,Class<T> clazz);
}
