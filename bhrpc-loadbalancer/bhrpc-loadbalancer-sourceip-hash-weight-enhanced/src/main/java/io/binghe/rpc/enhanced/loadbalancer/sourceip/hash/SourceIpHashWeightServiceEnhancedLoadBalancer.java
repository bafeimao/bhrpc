package io.binghe.rpc.enhanced.loadbalancer.sourceip.hash;

import io.binghe.rpc.loadbalancer.base.BaseEnhancedServiceLoadBalancer;
import io.binghe.rpc.protocol.meta.ServiceMeta;
import io.binghe.rpc.spi.annotation.SPIClass;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author You Chuande
 */
@SPIClass
public class SourceIpHashWeightServiceEnhancedLoadBalancer extends BaseEnhancedServiceLoadBalancer {
    private static final Logger log = LoggerFactory.getLogger(SourceIpHashWeightServiceEnhancedLoadBalancer.class);

    @Override
    public ServiceMeta select(List<ServiceMeta> services, int hashCode, String sourceIp) {
        log.info("增强型基于权重的源IP地址哈希的负载均衡策略");
        services = this.getWeightServiceMetaList(services);
        if (services == null || services.isEmpty()) {
            return null;
        }
        //传入的ip为空 默认返回第一个
        if (StringUtils.isEmpty(sourceIp)) {
            return services.get(0);
        }
        int resultHashCode = Math.abs(sourceIp.hashCode() + hashCode);
        return services.get(resultHashCode % services.size());
    }
}
