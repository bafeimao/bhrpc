package io.binghe.rpc.loadbalancer.sourceip.hash;

import io.binghe.rpc.loadbalancer.api.ServiceLoadBalancer;
import io.binghe.rpc.spi.annotation.SPIClass;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author You Chuande
 */
@SPIClass
public class SourceIpHashServiceLoadBalancer<T> implements ServiceLoadBalancer<T> {
    private static final Logger log = LoggerFactory.getLogger(SourceIpHashServiceLoadBalancer.class);

    @Override
    public T select(List<T> services, int hashCode, String sourceIp) {
        log.info("基于源ip的hash负载均衡策略");
        if (services == null || services.isEmpty()) {
            return null;
        }
        //传入的IP地址为空，直接返回第一个服务节点
        if (StringUtils.isEmpty(sourceIp)) {
            return services.get(0);
        }
        int resultHashCode = Math.abs(sourceIp.hashCode() + hashCode);
        return services.get(resultHashCode % services.size());
    }
}
