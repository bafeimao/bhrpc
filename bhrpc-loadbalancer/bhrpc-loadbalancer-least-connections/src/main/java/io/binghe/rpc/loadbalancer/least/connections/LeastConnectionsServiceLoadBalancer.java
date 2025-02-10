package io.binghe.rpc.loadbalancer.least.connections;

import io.binghe.rpc.loadbalancer.api.ServiceLoadBalancer;
import io.binghe.rpc.loadbalancer.context.ConnectionsContext;
import io.binghe.rpc.protocol.meta.ServiceMeta;
import io.binghe.rpc.spi.annotation.SPIClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author You Chuande
 */
@SPIClass
public class LeastConnectionsServiceLoadBalancer implements ServiceLoadBalancer<ServiceMeta> {
    private static final Logger log = LoggerFactory.getLogger(LeastConnectionsServiceLoadBalancer.class);

    @Override
    public ServiceMeta select(List<ServiceMeta> services, int hashCode, String sourceIp) {
        log.info("基于最小连接数的负载均衡策略");
        if (services == null || services.isEmpty()) {
            return null;
        }
        ServiceMeta serviceMeta = getNullServiceMeta(services);
        if (serviceMeta == null) {
            serviceMeta = getServiceMeta(services);
        }
        return serviceMeta;
    }

    private ServiceMeta getServiceMeta(List<ServiceMeta> servers) {
        ServiceMeta serviceMeta = servers.get(0);
        Integer serviceMetaCount = ConnectionsContext.getValue(serviceMeta);
        for (int i = 1; i < servers.size(); i++) {
            ServiceMeta meta = servers.get(i);
            Integer metaCount = ConnectionsContext.getValue(meta);
            if (serviceMetaCount > metaCount) {
                serviceMetaCount = metaCount;
                serviceMeta = meta;
            }
        }
        return serviceMeta;
    }

    //获取服务元数据列表中连接数为空的元数据，说明没有连接
    private ServiceMeta getNullServiceMeta(List<ServiceMeta> servers) {
        for (int i = 0; i < servers.size(); i++) {
            ServiceMeta serviceMeta = servers.get(i);
            if (ConnectionsContext.getValue(serviceMeta) == null) {
                return serviceMeta;
            }
        }
        return null;
    }
}
