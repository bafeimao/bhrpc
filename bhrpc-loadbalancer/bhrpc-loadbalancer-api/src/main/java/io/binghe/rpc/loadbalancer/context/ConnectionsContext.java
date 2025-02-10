package io.binghe.rpc.loadbalancer.context;

import io.binghe.rpc.protocol.meta.ServiceMeta;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author You Chuande
 */
public class ConnectionsContext {
    private static volatile Map<String, Integer> connectionsMap = new ConcurrentHashMap<>();

    public static void add(ServiceMeta serviceMeta) {
        String key = generateKey(serviceMeta);
        Integer count = connectionsMap.get(key);
        if (count == null) {
            count = 0;
        }
        count++;
        connectionsMap.put(key, count);
    }

    public static Integer getValue(ServiceMeta serviceMeta) {
        String key = generateKey(serviceMeta);
        return connectionsMap.get(key);
    }

    public static String generateKey(ServiceMeta serviceMeta) {
        return serviceMeta.getServiceAddr().concat(":").concat(String.valueOf(serviceMeta.getServicePort()));
    }
}
