package io.binghe.rpc.consumer.common.callback;

/**
 * @author You Chuande
 */
public interface AsyncRPCCallback {
    /**
     * 成功后的回调方法
     */
    void onSuccess(Object result);

    /**
     * 异常的回调方法
     */
    void onException(Exception e);

}
