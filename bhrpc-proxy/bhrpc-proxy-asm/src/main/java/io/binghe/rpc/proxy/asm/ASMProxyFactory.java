package io.binghe.rpc.proxy.asm;

import io.binghe.rpc.proxy.api.BaseProxyFactory;
import io.binghe.rpc.proxy.api.ProxyFactory;
import io.binghe.rpc.proxy.asm.proxy.ASMProxy;
import io.binghe.rpc.spi.annotation.SPIClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author You Chuande
 */
@SPIClass
public class ASMProxyFactory<T> extends BaseProxyFactory<T> implements ProxyFactory {

    private static final Logger log = LoggerFactory.getLogger(ASMProxyFactory.class);

    @Override
    public <T> T getProxy(Class<T> clazz) {
        try {
            log.info("基于ASM的代理");
            return (T) ASMProxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{clazz}, objectProxy);
        } catch (Exception e) {
            log.error("ASM代理异常", e);
        }
        return null;
    }
}
