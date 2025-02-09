package io.binghe.rpc.spi.factory;

import io.binghe.rpc.spi.annotation.SPI;
import io.binghe.rpc.spi.annotation.SPIClass;
import io.binghe.rpc.spi.loader.ExtensionLoader;

import java.util.Optional;

/**
 * @author You Chuande
 */
@SPIClass
public class SpiExtensionFactory implements ExtensionFactory {
    @Override
    public <T> T getExtension(String key, Class<T> clazz) {
        return Optional
                .ofNullable(clazz)
                .filter(Class::isInterface)
                .filter(cls -> cls.isAnnotationPresent(SPI.class))
                .map(ExtensionLoader::getExtensionLoader)
                .map(ExtensionLoader::getDefaultSpiClassInstance)
                .orElse(null);
    }
}
