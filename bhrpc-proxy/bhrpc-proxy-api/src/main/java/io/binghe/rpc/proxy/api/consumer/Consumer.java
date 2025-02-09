package io.binghe.rpc.proxy.api.consumer;

import io.binghe.rpc.protocol.RpcProtocol;
import io.binghe.rpc.protocol.request.RpcRequest;
import io.binghe.rpc.proxy.api.future.RPCFuture;
import io.binghe.rpc.registry.api.RegistryService;

/**
 * @author You Chuande
 */
public interface Consumer {
    /**
     * 消费者发送 request请求
     */
    RPCFuture sendRequest(RpcProtocol<RpcRequest> protocol, RegistryService registryService) throws Exception;
}
