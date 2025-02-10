package io.binghe.rpc.registry.api;

import io.binghe.rpc.protocol.meta.ServiceMeta;
import io.binghe.rpc.registry.api.config.RegistryConfig;

import java.io.IOException;

/**
 * @author You Chuande
 */
public interface RegistryService {
    /**
     * 服务注册
     *
     * @param serviceMeta 服务元信息
     * @throws Exception 异常
     */
    void register(ServiceMeta serviceMeta) throws Exception;

    /**
     * 服务取消注册
     *
     * @param serviceMeta 服务元信息
     * @throws Exception 异常
     */
    void unRegister(ServiceMeta serviceMeta) throws Exception;

    /**
     * 服务发现
     *
     * @param serviceName    服务名称
     * @param invokeHashCode 调用哈希值
     * @return 服务元信息
     * @throws Exception 异常
     */
    ServiceMeta discovery(String serviceName, int invokeHashCode,String sourceIp) throws Exception;

    /**
     * 销毁
     *
     * @throws IOException 异常
     */
    void destroy() throws IOException;

    /**
     * 默认初始化方法
     *
     * @param registryConfig 注册中心配置
     * @throws Exception 异常
     */
    default void init(RegistryConfig registryConfig) throws Exception {

    }

}
