package io.binghe.rpc.enhanced.loadbalancer.random.weight;

import io.binghe.rpc.loadbalancer.base.BaseEnhancedServiceLoadBalancer;
import io.binghe.rpc.protocol.meta.ServiceMeta;
import io.binghe.rpc.spi.annotation.SPIClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

/**
 * @author You Chuande
 */
@SPIClass
public class RandomWeightServiceEnhancedLoadBalancer extends BaseEnhancedServiceLoadBalancer {
    private static final Logger log = LoggerFactory.getLogger(RandomWeightServiceEnhancedLoadBalancer.class);

    @Override
    public ServiceMeta select(List<ServiceMeta> services, int hashCode, String sourceIp) {
        log.info("基于增强型加权随机算法的负载均衡策略");
        services = this.getWeightServiceMetaList(services);
        if (services == null || services.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int index = random.nextInt(services.size());
        return services.get(index);
    }
}
