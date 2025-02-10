package io.binghe.rpc.enhanced.loadbalancer.consistenthash;

import io.binghe.rpc.loadbalancer.base.BaseEnhancedServiceLoadBalancer;
import io.binghe.rpc.protocol.meta.ServiceMeta;
import io.binghe.rpc.spi.annotation.SPIClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author You Chuande
 */
@SPIClass
public class ZKConsistentHashEnhancedLoadBalancer extends BaseEnhancedServiceLoadBalancer {
    private final static int VIR_NODE_SIZE = 10;
    public static final String VIRTUAL_NODE_SPLIT = "#";
    private static final Logger log = LoggerFactory.getLogger(ZKConsistentHashEnhancedLoadBalancer.class);

    @Override
    public ServiceMeta select(List<ServiceMeta> services, int hashCode, String sourceIp) {
        log.info("基于Zookeeper增强型一致性Hash算法的负载均衡策略");
        TreeMap<Integer, ServiceMeta> ring =
                makeConsistentHashRing(services);
        return allocateNode(ring, hashCode);
    }

    private ServiceMeta allocateNode(TreeMap<Integer, ServiceMeta> ring, int hashCode) {
        Map.Entry<Integer, ServiceMeta> entry = ring.ceilingEntry(hashCode);
        if (entry == null) {
            entry = ring.firstEntry();
        }
        if (entry == null) {
            throw new RuntimeException("not discover useful service,please register service in registry center.");
        }
        return entry.getValue();
    }

    private TreeMap<Integer, ServiceMeta> makeConsistentHashRing(List<ServiceMeta> services) {
        TreeMap<Integer, ServiceMeta> ring = new TreeMap<>();
        for (ServiceMeta service : services) {
            for (int i = 0; i < VIR_NODE_SIZE; i++) {
                ring.put((buildServiceInstanceKey(service) + VIRTUAL_NODE_SPLIT + i).hashCode(), service);
            }
        }
        return ring;
    }

    private String buildServiceInstanceKey(ServiceMeta instance) {
        return String.join(":", instance.getServiceAddr(), String.valueOf(instance.getServicePort()));
    }
}
