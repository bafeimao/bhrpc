package io.binghe.rpc.proxy.api.async;

import io.binghe.rpc.proxy.api.future.RPCFuture;

/**
 * @author You Chuande
 */
public interface IAsyncObjectProxy {
    /**
     * 异步代理对象调用方法
     *
     * @param funcName 方法名称
     * @param args     方法参数
     * @return RPCFuture
     */
    RPCFuture call(String funcName, Object... args);
}
