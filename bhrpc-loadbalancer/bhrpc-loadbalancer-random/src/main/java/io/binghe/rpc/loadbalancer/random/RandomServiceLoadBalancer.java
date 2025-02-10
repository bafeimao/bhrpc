package io.binghe.rpc.loadbalancer.random;

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
public class RandomServiceLoadBalancer<T> implements ServiceLoadBalancer<T> {

    private static final Logger log = LoggerFactory.getLogger(RandomServiceLoadBalancer.class);

    @Override
    public T select(List<T> services, int hashCode,String sourceIp) {
        log.info("基于随机算法的负载均衡策略");
        if (services == null || services.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int index = random.nextInt(services.size());
        return services.get(index);
    }
}
