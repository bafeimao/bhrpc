package io.binghe.rpc.protocol.meta;

import java.io.Serializable;

/**
 * @author You Chuande
 */
public class ServiceMeta implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 服务版本
     */
    private String serviceVersion;

    /**
     * 服务地址
     */
    private String serviceAddr;

    /**
     * 服务端口
     */
    private int servicePort;

    /**
     * 服务分组
     */
    private String serviceGroup;

    /**
     * 权重
     */
    private int weight;

    public ServiceMeta() {
    }

    public ServiceMeta(String serviceName, String serviceVersion, String serviceGroup, String serviceAddr, int servicePort
            , int weight) {
        this.serviceName = serviceName;
        this.serviceVersion = serviceVersion;
        this.serviceAddr = serviceAddr;
        this.servicePort = servicePort;
        this.serviceGroup = serviceGroup;
        this.weight = weight;
    }

    public String getServiceName() {
        return serviceName;
    }

    public ServiceMeta setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public ServiceMeta setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
        return this;
    }

    public String getServiceAddr() {
        return serviceAddr;
    }

    public ServiceMeta setServiceAddr(String serviceAddr) {
        this.serviceAddr = serviceAddr;
        return this;
    }

    public int getServicePort() {
        return servicePort;
    }

    public ServiceMeta setServicePort(int servicePort) {
        this.servicePort = servicePort;
        return this;
    }

    public String getServiceGroup() {
        return serviceGroup;
    }

    public ServiceMeta setServiceGroup(String serviceGroup) {
        this.serviceGroup = serviceGroup;
        return this;
    }

    public int getWeight() {
        return weight;
    }

    public ServiceMeta setWeight(int weight) {
        this.weight = weight;
        return this;
    }
}
