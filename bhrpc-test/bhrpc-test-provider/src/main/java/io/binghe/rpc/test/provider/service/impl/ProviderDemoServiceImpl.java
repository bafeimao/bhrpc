package io.binghe.rpc.test.provider.service.impl;

import io.binghe.rpc.annotation.RpcService;
import io.binghe.rpc.test.api.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author You Chuande
 */
@RpcService(interfaceClass = DemoService.class, interfaceClassName = "io.binghe.rpc.test.api.DemoService",
        version = "1.0.0", group = "binghe")
public class ProviderDemoServiceImpl implements DemoService {

    private final Logger log = LoggerFactory.getLogger(ProviderDemoServiceImpl.class);

    @Override
    public String hello(String name) {
        log.info("调用hello方法传入的参数为====>>>>{}", name);
        return "hello " + name;
    }
}
