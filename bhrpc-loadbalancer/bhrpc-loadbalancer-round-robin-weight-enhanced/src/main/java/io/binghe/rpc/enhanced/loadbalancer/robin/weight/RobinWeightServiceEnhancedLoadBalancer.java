package io.binghe.rpc.enhanced.loadbalancer.robin.weight;

import io.binghe.rpc.loadbalancer.base.BaseEnhancedServiceLoadBalancer;
import io.binghe.rpc.protocol.meta.ServiceMeta;
import io.binghe.rpc.spi.annotation.SPIClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author You Chuande
 */
@SPIClass
public class RobinWeightServiceEnhancedLoadBalancer extends BaseEnhancedServiceLoadBalancer {

    private static final Logger log = LoggerFactory.getLogger(RobinWeightServiceEnhancedLoadBalancer.class);
    private volatile AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public ServiceMeta select(List<ServiceMeta> services, int hashCode, String sourceIp) {
        log.info("基于增强型加权轮询算法的负载均衡策略");
        services = this.getWeightServiceMetaList(services);
        if (services == null || services.isEmpty()) {
            return null;
        }
        int index = atomicInteger.getAndIncrement();
        if (index >= Integer.MAX_VALUE - 10000) {
            atomicInteger.set(0);
        }
        return services.get(index % services.size());
    }
}
