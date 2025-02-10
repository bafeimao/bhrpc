package io.binghe.rpc.loadbalancer.robin.weight;

import io.binghe.rpc.loadbalancer.api.ServiceLoadBalancer;
import io.binghe.rpc.spi.annotation.SPIClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author You Chuande
 */
@SPIClass
public class RobinWeightServiceLoadBalancer<T> implements ServiceLoadBalancer<T> {

    private static final Logger log = LoggerFactory.getLogger(RobinWeightServiceLoadBalancer.class);
    private volatile AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public T select(List<T> services, int hashCode, String sourceIp) {
        log.info("基于加权轮询算法的负载均衡策略");
        if (services == null || services.isEmpty()) {
            return null;
        }
        hashCode = Math.abs(hashCode);
        int count = hashCode % services.size();
        if (count <= 0) {
            count = services.size();
        }
        int index = atomicInteger.incrementAndGet();
        if (index >= Integer.MAX_VALUE - 10000) {
            atomicInteger.set(0);
        }
        return services.get(index % count);
    }
}
