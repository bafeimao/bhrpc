package io.binghe.rpc.test.spi.service.impl;

import io.binghe.rpc.spi.annotation.SPIClass;
import io.binghe.rpc.test.spi.service.SPIService;

/**
 * @author You Chuande
 */
@SPIClass
public class SPIServiceImpl implements SPIService {
    @Override
    public String hello(String name) {
        return "hello " + name;
    }
}
