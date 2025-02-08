package io.binghe.rpc.registry.api.config;

import java.io.Serializable;

/**
 * @author You Chuande
 */
public class RegistryConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 注册地址
     */
    private String registryAddr;

    /**
     * 注册类型
     */
    private String registryType;

    public RegistryConfig(String registryAddr, String registryType) {
        this.registryAddr = registryAddr;
        this.registryType = registryType;
    }

    public String getRegistryAddr() {
        return registryAddr;
    }

    public RegistryConfig setRegistryAddr(String registryAddr) {
        this.registryAddr = registryAddr;
        return this;
    }

    public String getRegistryType() {
        return registryType;
    }

    public RegistryConfig setRegistryType(String registryType) {
        this.registryType = registryType;
        return this;
    }
}
