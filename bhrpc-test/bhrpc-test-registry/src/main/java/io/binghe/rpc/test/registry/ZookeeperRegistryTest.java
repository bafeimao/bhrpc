package io.binghe.rpc.test.registry;

import io.binghe.rpc.common.helper.RpcServiceHelper;
import io.binghe.rpc.protocol.meta.ServiceMeta;
import io.binghe.rpc.registry.api.RegistryService;
import io.binghe.rpc.registry.api.config.RegistryConfig;
import io.binghe.rpc.registry.zookeeper.ZookeeperRegistryService;
import org.junit.Before;
import org.junit.Test;

/**
 * @author You Chuande
 */
public class ZookeeperRegistryTest {
    private RegistryService registryService;
    private ServiceMeta serviceMeta;

    @Before
    public void init() throws Exception {
        RegistryConfig registryConfig = new RegistryConfig("127.0.0.1:3181", "zookeeper");
        this.registryService = new ZookeeperRegistryService();
        this.registryService.init(registryConfig);
        this.serviceMeta = new ServiceMeta(ZookeeperRegistryTest.class.getName(), "1.0.0",
                "binghe", "127.0.0.1",
                8080);
    }

    @Test
    public void testRegister() throws Exception {
        registryService.register(serviceMeta);
    }

    @Test
    public void testUnRegister() throws Exception {
        registryService.unRegister(serviceMeta);
    }

    @Test
    public void testDiscovery() throws Exception {
        ServiceMeta serviceMeta = registryService.discovery(RpcServiceHelper.buildServiceKey(ZookeeperRegistryTest.class.getName(),
                "1.0.0", "binghe"), "binghe".hashCode());
        System.out.println(serviceMeta);
    }

    @Test
    public void testDestroy() throws Exception {
        registryService.destroy();
    }
}
