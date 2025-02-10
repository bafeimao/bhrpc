package io.binghe.rpc.loadbalancer.consistenthash;

import io.binghe.rpc.loadbalancer.api.ServiceLoadBalancer;
import io.binghe.rpc.protocol.meta.ServiceMeta;
import io.binghe.rpc.spi.annotation.SPIClass;
import org.apache.curator.x.discovery.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author You Chuande
 */
@SPIClass
public class ZKConsistentHashLoadBalancer implements ServiceLoadBalancer<ServiceInstance<ServiceMeta>> {
    private static final Logger log = LoggerFactory.getLogger(ZKConsistentHashLoadBalancer.class);

    private final static int VIRTUAL_NODE_SIZE = 10;

    private final static String VIRTUAL_NODE_SPLIT = "#";

    @Override
    public ServiceInstance<ServiceMeta> select(List<ServiceInstance<ServiceMeta>> services, int hashCode, String sourceIp) {
        log.info("基于zookeeper一致性hash的负载均衡策略");
        TreeMap<Integer, ServiceInstance<ServiceMeta>> ring =
                makeConsistentHashRing(services);
        return allocateNode(ring, hashCode);
    }

    private ServiceInstance<ServiceMeta> allocateNode(TreeMap<Integer, ServiceInstance<ServiceMeta>> ring, int hashCode) {
        Map.Entry<Integer, ServiceInstance<ServiceMeta>> entry = ring.ceilingEntry(hashCode);
        if (entry == null) {
            entry = ring.firstEntry();
        }
        if (entry == null) {
            throw new RuntimeException("not discover useful service,please register service in registry center.");
        }
        return entry.getValue();
    }

    private TreeMap<Integer, ServiceInstance<ServiceMeta>> makeConsistentHashRing(List<ServiceInstance<ServiceMeta>> services) {
        TreeMap<Integer, ServiceInstance<ServiceMeta>> ring = new TreeMap<>();
        for (ServiceInstance<ServiceMeta> service : services) {
            for (int i = 0; i < VIRTUAL_NODE_SIZE; i++) {
                ring.put((buildServiceInstanceKey(service) + VIRTUAL_NODE_SPLIT + i).hashCode(), service);
            }
        }
        return ring;
    }

    private String buildServiceInstanceKey(ServiceInstance<ServiceMeta> instance) {
        ServiceMeta payload = instance.getPayload();
        return String.join(":", payload.getServiceAddr(), String.valueOf(payload.getServicePort()));
    }
}
