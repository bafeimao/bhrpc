package io.binghe.rpc.provider.common.scanner;

import io.binghe.rpc.annotation.RpcService;
import io.binghe.rpc.common.helper.RpcServiceHelper;
import io.binghe.rpc.common.scanner.ClassScanner;
import io.binghe.rpc.constants.RpcConstants;
import io.binghe.rpc.protocol.meta.ServiceMeta;
import io.binghe.rpc.registry.api.RegistryService;
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
            String host, int port,
            String scanPackage,
            RegistryService registryService
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
                    ServiceMeta serviceMeta = new ServiceMeta(getServiceName(rpcService),
                            rpcService.version(), rpcService.group(), host, port, getWeight(rpcService.weight()));
                    registryService.register(serviceMeta);
                    handlerMap.put(RpcServiceHelper.buildServiceKey(serviceMeta.getServiceName(),
                                    serviceMeta.getServiceVersion(), serviceMeta.getServiceGroup()),
                            clazz.newInstance());
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

    public static int getWeight(int weight) {
        if (weight < RpcConstants.SERVICE_WEIGHT_MIN) {
            return RpcConstants.SERVICE_WEIGHT_MIN;
        }
        if (weight > RpcConstants.SERVICE_WEIGHT_MAX) {
            return RpcConstants.SERVICE_WEIGHT_MAX;
        }
        return weight;
    }
}
