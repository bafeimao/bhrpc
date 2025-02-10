package io.binghe.rpc.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

/**
 * @author You Chuande
 */
public class IpUtils {
    private static final Logger log = LoggerFactory.getLogger(IpUtils.class);

    public static InetAddress getLocalInetAddress() {
        try {
            return InetAddress.getLocalHost();
        } catch (Exception e) {
            log.error("获取本地IP地址失败", e);
        }
        return null;
    }

    public static String getLocalAddress() {
        return getLocalInetAddress().toString();
    }

    public static String getLocalHostName() {
        return getLocalInetAddress().getHostName();
    }

    public static String getLocalHostIp() {
        return getLocalInetAddress().getHostAddress();
    }
}
