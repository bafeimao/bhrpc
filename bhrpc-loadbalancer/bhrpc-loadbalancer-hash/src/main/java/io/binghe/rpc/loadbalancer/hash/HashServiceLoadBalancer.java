package io.binghe.rpc.loadbalancer.hash;

import io.binghe.rpc.loadbalancer.api.ServiceLoadBalancer;
import io.binghe.rpc.spi.annotation.SPIClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author You Chuande
 */
@SPIClass
public class HashServiceLoadBalancer<T> implements ServiceLoadBalancer<T> {
    private static final Logger log = LoggerFactory.getLogger(HashServiceLoadBalancer.class);

    @Override
    public T select(List<T> services, int hashCode) {
        log.info("基于hash的负载均衡策略");
        if (services == null || services.size() == 0) {
            return null;
        }
        int index = Math.abs(hashCode) % services.size();
        return services.get(index);
    }
}
