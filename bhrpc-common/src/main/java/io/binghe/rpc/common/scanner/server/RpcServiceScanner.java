package io.binghe.rpc.common.scanner.server;

import io.binghe.rpc.annotation.RpcService;
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
                    log.info("当前标注了@RpcService注解的类实例名称");
                    log.info("@RpcService注解上标注的属性信息如下");
                    log.info("interfaceClass=====>>>");
                    log.info(rpcService.interfaceClass().getName());
                    log.info("interfaceClassName");
                    log.info(rpcService.interfaceClassName());
                    log.info("version===>>>" + rpcService.version());
                    log.info("group===>>>" + rpcService.group());
                    String serviceName = rpcService.interfaceClass().getName();
                    String key = serviceName.concat(rpcService.version()).concat(rpcService.group());
                    handlerMap.put(key, clazz.newInstance());
                }
            } catch (Exception e) {
                log.error("scan classes throws exception:{}", e);
            }
        });
        return handlerMap;
    }
}
