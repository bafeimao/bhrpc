package io.binghe.rpc.loadbalancer.helper;

import com.alibaba.nacos.api.naming.pojo.Instance;
import io.binghe.rpc.protocol.meta.ServiceMeta;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;
import java.util.Map;
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

    public static List<ServiceMeta> getNacosServiceMetaList(List<Instance> serviceInstances) {
        if (serviceInstances == null || serviceInstances.isEmpty()
                || cacheServiceMeta.size() == serviceInstances.size()) {
            return cacheServiceMeta;
        }
        //先清空缓存中的数据
        cacheServiceMeta.clear();
        serviceInstances.forEach(serviceInstance -> {
            Map<String, String> metadata = serviceInstance.getMetadata();
            ServiceMeta serviceMeta = new ServiceMeta();
            serviceMeta.setServiceAddr(serviceInstance.getIp());
            serviceMeta.setServicePort(serviceInstance.getPort());
            serviceMeta.setServiceName(metadata.get("serviceName"));
            serviceMeta.setServiceVersion(metadata.get("serviceVersion"));
            serviceMeta.setServiceGroup(metadata.get("serviceGroup"));
            serviceMeta.setWeight(Integer.parseInt(metadata.get("weight")));
            cacheServiceMeta.add(serviceMeta);
        });
        return cacheServiceMeta;
    }
}
