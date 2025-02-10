package io.binghe.rpc.registry.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.naming.NamingMaintainService;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import io.binghe.rpc.common.helper.RpcServiceHelper;
import io.binghe.rpc.constants.RpcConstants;
import io.binghe.rpc.loadbalancer.api.ServiceLoadBalancer;
import io.binghe.rpc.loadbalancer.helper.ServiceLoadBalancerHelper;
import io.binghe.rpc.protocol.meta.ServiceMeta;
import io.binghe.rpc.registry.api.RegistryService;
import io.binghe.rpc.registry.api.config.RegistryConfig;
import io.binghe.rpc.spi.annotation.SPIClass;
import io.binghe.rpc.spi.loader.ExtensionLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author You Chuande
 */
@SPIClass
public class NacosRegistryService implements RegistryService {

    private NamingService namingService;

    private ConfigService configService;

    private NamingMaintainService namingMaintainService;

    private ServiceLoadBalancer<ServiceMeta> serviceLoadBalancer;
    private ServiceLoadBalancer<ServiceMeta> serviceEnhancedLoadBalancer;


    @Override
    public void init(RegistryConfig registryConfig) throws Exception {
        namingService = NacosFactory.createNamingService(registryConfig.getRegistryAddr());
        configService = NacosFactory.createConfigService(registryConfig.getRegistryAddr());
        namingMaintainService = NacosFactory.createMaintainService(registryConfig.getRegistryAddr());
        if (registryConfig.getRegistryLoadBalancerType().toLowerCase().contains(RpcConstants.SERVICE_ENHANCED_LOAD_BALANCER_PREFIX)) {
            this.serviceEnhancedLoadBalancer = ExtensionLoader.getExtension(ServiceLoadBalancer.class, registryConfig.getRegistryLoadBalancerType());
        } else {
            this.serviceLoadBalancer = ExtensionLoader.getExtension(ServiceLoadBalancer.class, registryConfig.getRegistryLoadBalancerType());
        }
    }

    @Override
    public void register(ServiceMeta serviceMeta) throws Exception {
        Instance instance = new Instance();
        instance.setServiceName(serviceMeta.getServiceName());
        instance.setIp(serviceMeta.getServiceAddr());
        instance.setPort(serviceMeta.getServicePort());
        Map<String, String> metadata = getMetaDataByServiceMeta(serviceMeta);
        instance.setMetadata(metadata);
        namingService.registerInstance(RpcServiceHelper.buildServiceKey(serviceMeta.getServiceName(),
                serviceMeta.getServiceVersion(), serviceMeta.getServiceGroup()), instance);
    }

    private static Map<String, String> getMetaDataByServiceMeta(ServiceMeta serviceMeta) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("serviceVersion", serviceMeta.getServiceVersion());
        metadata.put("serviceGroup", serviceMeta.getServiceGroup());
        metadata.put("serviceAddr", serviceMeta.getServiceAddr());
        metadata.put("servicePort", String.valueOf(serviceMeta.getServicePort()));
        metadata.put("weight", String.valueOf(serviceMeta.getWeight()));
        return metadata;
    }

    @Override
    public void unRegister(ServiceMeta serviceMeta) throws Exception {
        Instance instance = new Instance();
        instance.setServiceName(serviceMeta.getServiceName());
        instance.setIp(serviceMeta.getServiceAddr());
        instance.setPort(serviceMeta.getServicePort());
        Map<String, String> metadata = getMetaDataByServiceMeta(serviceMeta);
        instance.setMetadata(metadata);
        namingService.deregisterInstance(RpcServiceHelper.buildServiceKey(serviceMeta.getServiceName(),
                serviceMeta.getServiceVersion(), serviceMeta.getServiceGroup()), serviceMeta.getServiceGroup(), instance);
    }

    @Override
    public ServiceMeta discovery(String serviceName, int invokeHashCode, String sourceIp) throws Exception {
        List<Instance> instances = namingService.getAllInstances(serviceName);
        if (serviceLoadBalancer != null) {
            return getServiceMetaInstance(invokeHashCode, sourceIp,
                    instances);
        }
        return this.serviceEnhancedLoadBalancer.select(ServiceLoadBalancerHelper.getNacosServiceMetaList(
                instances), invokeHashCode, sourceIp);
    }

    private ServiceMeta getServiceMetaInstance(int invokeHashCode, String sourceIp, List<Instance> instances) {
        return this.serviceLoadBalancer.select(ServiceLoadBalancerHelper.getNacosServiceMetaList(
                instances), invokeHashCode, sourceIp);
    }

    @Override
    public void destroy() throws Exception {
        namingService.shutDown();
    }
}
