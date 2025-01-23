package io.binghe.rpc.common.id;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author You Chuande
 */
public class IdFactory {
    private static final AtomicLong REQUEST_ID_GEN = new AtomicLong(0);

    public static long getId() {
        return REQUEST_ID_GEN.incrementAndGet();
    }
}
