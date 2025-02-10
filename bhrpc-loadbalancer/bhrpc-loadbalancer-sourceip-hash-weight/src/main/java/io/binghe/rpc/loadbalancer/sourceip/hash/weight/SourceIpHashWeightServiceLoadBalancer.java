package io.binghe.rpc.loadbalancer.sourceip.hash.weight;

import io.binghe.rpc.loadbalancer.api.ServiceLoadBalancer;
import io.binghe.rpc.spi.annotation.SPIClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author You Chuande
 */
@SPIClass
public class SourceIpHashWeightServiceLoadBalancer<T> implements ServiceLoadBalancer<T> {

    private static final Logger log = LoggerFactory.getLogger(SourceIpHashWeightServiceLoadBalancer.class);

    @Override
    public T select(List<T> services, int hashCode, String sourceIp) {
        log.info("基于源ip的hash加权负载均衡策略");
        if (services == null || services.isEmpty()) {
            return null;
        }
        //传入的IP地址为空，直接返回第一个服务节点
        if (sourceIp == null) {
            return services.get(0);
        }
        int count = Math.abs(hashCode) % services.size();
        if (count == 0) {
            count = services.size();
        }
        int resultHashCode = Math.abs(sourceIp.hashCode() + hashCode);
        return services.get(resultHashCode % count);
    }
}
