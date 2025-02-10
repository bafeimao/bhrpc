package io.binghe.rpc.enhanced.loadbalancer.hash.weight;

import io.binghe.rpc.loadbalancer.base.BaseEnhancedServiceLoadBalancer;
import io.binghe.rpc.protocol.meta.ServiceMeta;
import io.binghe.rpc.spi.annotation.SPIClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author You Chuande
 */
@SPIClass
public class HashWeightServiceEnhancedLoadBalancer extends BaseEnhancedServiceLoadBalancer {
    private static final Logger log = LoggerFactory.getLogger(HashWeightServiceEnhancedLoadBalancer.class);

    @Override
    public ServiceMeta select(List<ServiceMeta> services, int hashCode, String sourceIp) {
        log.info("基于增强型加权Hash算法的负载均衡策略");
        services = this.getWeightServiceMetaList(services);
        if (services == null || services.isEmpty()) {
            return null;
        }
        int index = Math.abs(hashCode) % services.size();
        return services.get(index);
    }
}
