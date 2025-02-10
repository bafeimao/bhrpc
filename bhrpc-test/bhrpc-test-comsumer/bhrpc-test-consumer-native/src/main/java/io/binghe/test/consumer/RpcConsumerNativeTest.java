package io.binghe.test.consumer;

import io.binghe.rpc.consumer.RpcClient;
import io.binghe.rpc.proxy.api.async.IAsyncObjectProxy;
import io.binghe.rpc.proxy.api.future.RPCFuture;
import io.binghe.rpc.test.api.DemoService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author You Chuande
 */
public class RpcConsumerNativeTest {
    private static final Logger log = LoggerFactory.getLogger(RpcConsumerNativeTest.class);

    private RpcClient rpcClient;

    @Before
    public void initRpcClient() {
        rpcClient = new RpcClient("127.0.0.1:3181", "zookeeper",
                "enhanced_zkconsistenthash", "1.0.0",
                "cglib", "binghe", "protostuff", 3000, false, false);
    }

    @Test
    public void testInterfaceRpc() {
        DemoService demoService = rpcClient.create(DemoService.class);
        String result = demoService.hello("binghe");
        log.info("返回的结果数据===>>>>>:{}", result);
        rpcClient.shutdown();
    }

}
