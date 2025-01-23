package io.binghe.rpc.common.scanner.reference;

import io.binghe.rpc.annotation.RpcReference;
import io.binghe.rpc.common.scanner.ClassScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author You Chuande
 */
public class RpcReferenceScanner extends ClassScanner {

    private static final Logger log = LoggerFactory.getLogger(RpcReferenceScanner.class);

    public static Map<String, Object> doScannerWithRpcReferenceAnnotationFilter(
            String scanPackage
    ) throws Exception {
        Map<String, Object> handlerMap = new HashMap<>();
        List<String> classNameList = getClassNameList(scanPackage, true);
        if (classNameList == null || classNameList.isEmpty()) {
            return handlerMap;
        }
        classNameList.stream().forEach(className -> {
            try {
                Class<?> clazz = Class.forName(className);
                Field[] declaredFields = clazz.getDeclaredFields();
                Stream.of(declaredFields).forEach(field -> {
                    RpcReference rpcReference = field.getAnnotation(RpcReference.class);
                    if (rpcReference != null) {
                        //todo
                        log.info("当前标注了@RpcReference注解的字段名称===>>>" + field.getName());
                        log.info("@RpcReference注解行标注的属性信息如下:");
                        log.info("version ===>>" + rpcReference.version());
                        log.info("group ===>>" + rpcReference.group());
                        log.info("registryType ===>>" + rpcReference.registryType());
                        log.info("registryAddress ===>>" + rpcReference.registryAddress());
                    }
                });
            } catch (Exception e) {
                log.error("scan classes throws exception", e);
            }
        });
        return handlerMap;
    }
}
