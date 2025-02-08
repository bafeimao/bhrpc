package io.binghe.test.consumer;

import io.binghe.rpc.consumer.RpcClient;
import io.binghe.rpc.test.api.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author You Chuande
 */
public class RpcConsumerNativeTest {
    private static final Logger log = LoggerFactory.getLogger(RpcConsumerNativeTest.class);

    public static void main(String[] args) {
        RpcClient rpcClient = new RpcClient("1.0.0", "binghe", "jdk", 3000, false, false);
        DemoService demoService = rpcClient.create(DemoService.class);
        String result = demoService.hello("binghe");
        log.info("result:{}", result);
        rpcClient.shutdown();

    }
}
