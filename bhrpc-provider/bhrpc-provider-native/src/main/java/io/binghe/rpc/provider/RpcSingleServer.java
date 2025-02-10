package io.binghe.rpc.provider;

import io.binghe.rpc.provider.common.scanner.RpcServiceScanner;
import io.binghe.rpc.provider.common.server.base.BaseServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author You Chuande
 */
public class RpcSingleServer extends BaseServer {
    private final Logger log = LoggerFactory.getLogger(RpcSingleServer.class);

    public RpcSingleServer(String serverAddress, String registryAddress, String registryType,
                           String registryLoadBalancerType,
                           String scanPackage, String reflectType) {
        super(serverAddress, registryAddress, registryType, registryLoadBalancerType, reflectType);
        try {
            this.handlerMap =
                    RpcServiceScanner.doScannerWithRpcServiceAnnotationFilterAndRegistryService(this.host, this.port, scanPackage, registryService);
        } catch (Exception e) {
            log.error("RpcServer init error", e);
        }
    }
}
