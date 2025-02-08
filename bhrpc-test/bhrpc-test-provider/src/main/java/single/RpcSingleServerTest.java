package single;

import io.binghe.rpc.provider.RpcSingleServer;
import org.junit.Test;

/**
 * @author You Chuande
 */
public class RpcSingleServerTest {
    @Test
    public void startRpcSingleServer() {
        RpcSingleServer singleServer = new RpcSingleServer("127.0.0.1:27880", "io.binghe.rpc.test","cglib");
        singleServer.startNettyServer();
    }
}
