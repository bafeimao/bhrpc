package io.binghe.rpc.provider;

import io.binghe.rpc.common.scanner.server.RpcServiceScanner;
import io.binghe.rpc.provider.common.server.base.BaseServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author You Chuande
 */
public class RpcSingleServer extends BaseServer {
    private final Logger log = LoggerFactory.getLogger(RpcSingleServer.class);

    public RpcSingleServer(String serverAddress, String scanPackage,String reflectType) {
        super(serverAddress,reflectType);
        try {
            this.handlerMap =
                    RpcServiceScanner.doScannerWithRpcServiceAnnotationFilterAndRegistryService(scanPackage);
        } catch (Exception e) {
            log.error("RpcServer init error", e);
        }
    }
}
