package io.binghe.rpc.loadbalancer.api;

import io.binghe.rpc.spi.annotation.SPI;

import java.util.List;

/**
 * @author You Chuande
 */

@SPI
public interface ServiceLoadBalancer<T> {
    /**
     * 以负载均衡的方式选择一个服务节点
     *
     * @param services 服务节点列表
     * @param hashCode 调用哈希值
     * @return 服务节点
     */
    T select(List<T> services, int hashCode);
}
