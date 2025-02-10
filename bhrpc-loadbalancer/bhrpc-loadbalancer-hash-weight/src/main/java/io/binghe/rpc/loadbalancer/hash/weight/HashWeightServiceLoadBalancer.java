package io.binghe.rpc.loadbalancer.hash.weight;

import io.binghe.rpc.loadbalancer.api.ServiceLoadBalancer;
import io.binghe.rpc.spi.annotation.SPIClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author You Chuande
 */
@SPIClass
public class HashWeightServiceLoadBalancer<T> implements ServiceLoadBalancer<T> {
    private static final Logger log = LoggerFactory.getLogger(HashWeightServiceLoadBalancer.class);

    @Override
    public T select(List<T> services, int hashCode) {
        log.info("基于加权hash的负载均衡策略");
        if (services == null || services.size() == 0) {
            return null;
        }
        hashCode = Math.abs(hashCode);
        int count = hashCode % services.size();
        if (count <= 0) {
            count = services.size();
        }
        int index = hashCode % count;
        return services.get(index);
    }
}
