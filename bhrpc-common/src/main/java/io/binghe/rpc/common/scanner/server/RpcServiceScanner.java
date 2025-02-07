package io.binghe.rpc.common.scanner.server;

import io.binghe.rpc.annotation.RpcService;
import io.binghe.rpc.common.helper.RpcServiceHelper;
import io.binghe.rpc.common.scanner.ClassScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author You Chuande
 */
public class RpcServiceScanner extends ClassScanner {
    private static final Logger log = LoggerFactory.getLogger(RpcServiceScanner.class);

    public static Map<String, Object> doScannerWithRpcServiceAnnotationFilterAndRegistryService(
            String scanPackage
            //String host, int port,
            // RegistryService registryService
    ) throws Exception {
        Map<String, Object> handlerMap = new HashMap<>();
        List<String> classNameList = getClassNameList(scanPackage, true);
        if (classNameList == null || classNameList.isEmpty()) {
            return handlerMap;
        }

        classNameList.stream().forEach((className) -> {
            try {
                Class<?> clazz = Class.forName(className);
                RpcService rpcService = clazz.getAnnotation(RpcService.class);
                if (rpcService != null) {
                    String serviceName = getServiceName(rpcService);
                    String key = RpcServiceHelper.buildServiceKey(serviceName, rpcService.version(), rpcService.group());
                    handlerMap.put(key, clazz.newInstance());
                }
            } catch (Exception e) {
                log.error("scan classes throws exception:{}", e);
            }
        });
        return handlerMap;
    }

    public static String getServiceName(RpcService rpcService) {
        Class clazz = rpcService.interfaceClass();
        if (clazz == void.class) {
            return rpcService.interfaceClassName();
        }
        String serviceName = clazz.getName();
        if (serviceName.trim().isEmpty()) {
            serviceName = rpcService.interfaceClassName();
        }
        return serviceName;
    }
}
