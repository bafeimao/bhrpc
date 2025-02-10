package io.binghe.rpc.loadbalancer.random.weight;

import io.binghe.rpc.loadbalancer.api.ServiceLoadBalancer;
import io.binghe.rpc.spi.annotation.SPIClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

/**
 * @author You Chuande
 */
@SPIClass
public class RandomWeightServiceLoadBalancer<T> implements ServiceLoadBalancer<T> {
    private static final Logger log = LoggerFactory.getLogger(RandomWeightServiceLoadBalancer.class);

    @Override
    public T select(List<T> services, int hashCode) {
        log.info("基于加权随机算法的负载均衡策略");
        if (services == null || services.isEmpty()) {
            return null;
        }
        hashCode = Math.abs(hashCode);
        int count = hashCode % services.size();
        if (count < 1) {
            count = services.size();
        }
        Random random = new Random();
        int index = random.nextInt(count);
        return services.get(index);
    }
}
