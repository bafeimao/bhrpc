package io.binghe.rpc.consumer;

import io.binghe.rpc.common.exception.RegistryException;
import io.binghe.rpc.consumer.common.RpcConsumer;
import io.binghe.rpc.proxy.api.ProxyFactory;
import io.binghe.rpc.proxy.api.async.IAsyncObjectProxy;
import io.binghe.rpc.proxy.api.config.ProxyConfig;
import io.binghe.rpc.proxy.api.object.ObjectProxy;
import io.binghe.rpc.proxy.jdk.JdkProxyFactory;
import io.binghe.rpc.registry.api.RegistryService;
import io.binghe.rpc.registry.api.config.RegistryConfig;
import io.binghe.rpc.registry.zookeeper.ZookeeperRegistryService;
import io.binghe.rpc.spi.loader.ExtensionLoader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author You Chuande
 */
public class RpcClient {
    private final Logger log = LoggerFactory.getLogger(RpcClient.class);

    private String registryAddress;

    private String registryType;

    private String proxy;

    /**
     * 服务版本
     */
    private String serviceVersion;

    /**
     * 服务分组
     */
    private String serviceGroup;

    /**
     * 序列化类型
     */
    private String serializationType;

    /**
     * 超时时间
     */
    private long timeout;

    /**
     * 是否异步调用
     */
    private boolean async;

    /**
     * 是否单向调用
     */
    private boolean oneway;

    public RpcClient(String registryAddress, String registryType, String serviceVersion,String proxy, String serviceGroup, String serializationType, long timeout, boolean async, boolean oneway) {
        this.registryAddress = registryAddress;
        this.registryType = registryType;
        this.serviceVersion = serviceVersion;
        this.proxy = proxy;
        this.serviceGroup = serviceGroup;
        this.serializationType = serializationType;
        this.timeout = timeout;
        this.async = async;
        this.oneway = oneway;
    }

    public <T> T create(Class<T> interfaceClass) {
        ProxyFactory proxyFactory = ExtensionLoader.getExtension(ProxyFactory.class,proxy);
        proxyFactory.init(new ProxyConfig<>(
                interfaceClass, serviceVersion, serviceGroup, serializationType, timeout, getRegistryService(registryAddress, registryType), RpcConsumer.getInstance(), async, oneway
        ));
        return proxyFactory.getProxy(interfaceClass);
    }

    public <T> IAsyncObjectProxy createAsync(Class<T> interfaceClass) {
        return new ObjectProxy<T>(interfaceClass, serviceVersion, serviceGroup,
                serializationType, timeout, getRegistryService(registryAddress, registryType), RpcConsumer.getInstance(), async, oneway);
    }

    public void shutdown() {
        RpcConsumer.getInstance().close();
    }

    private RegistryService getRegistryService(String registryAddress, String registryType) {
        if (StringUtils.isEmpty(registryType)) {
            throw new IllegalArgumentException("registryType is null");
        }
        RegistryService registryService = new ZookeeperRegistryService();
        try {
            registryService.init(new RegistryConfig(registryAddress, registryType));
        } catch (Exception e) {
            log.error("RPC Client init registry service throws exception:", e);
            throw new RegistryException(e.getMessage(), e);
        }
        return registryService;
    }
}
