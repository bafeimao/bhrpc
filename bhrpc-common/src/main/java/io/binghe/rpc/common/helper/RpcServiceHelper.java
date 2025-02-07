package io.binghe.rpc.common.helper;

/**
 * @author You Chuande
 */
public class RpcServiceHelper {

    public static String buildServiceKey(String serviceName, String serviceVersion, String serviceGroup) {
        return String.join("#", serviceName, serviceVersion, serviceGroup);
    }


}
