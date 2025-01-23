package io.binghe.rpc.test.provider.service.impl;

import io.binghe.rpc.annotation.RpcService;
import io.binghe.rpc.test.provider.service.DemoService;

/**
 * @author You Chuande
 */
@RpcService(interfaceClass = DemoService.class, interfaceClassName = "io.binghe.rpc.test.provider.service.DemoService",
        version = "1.0.0", group = "binghe")
public class ProviderDemoServiceImpl implements DemoService {
}
