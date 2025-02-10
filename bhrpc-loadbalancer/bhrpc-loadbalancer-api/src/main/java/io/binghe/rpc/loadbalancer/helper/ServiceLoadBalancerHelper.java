package io.binghe.rpc.loadbalancer.helper;

import io.binghe.rpc.protocol.meta.ServiceMeta;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author You Chuande
 */
public class ServiceLoadBalancerHelper {
    private static volatile List<ServiceMeta> cacheServiceMeta = new CopyOnWriteArrayList<>();

    public static List<ServiceMeta> getServiceMetaList(List<ServiceInstance<ServiceMeta>> serviceInstances) {
        if (serviceInstances == null || serviceInstances.isEmpty()
                || cacheServiceMeta.size() == serviceInstances.size()) {
            return cacheServiceMeta;
        }
        //先清空缓存中的数据
        cacheServiceMeta.clear();
        serviceInstances.forEach(serviceInstance -> cacheServiceMeta.add(serviceInstance.getPayload()));
        return cacheServiceMeta;
    }
}
