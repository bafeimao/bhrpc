package io.binghe.rpc.test.spi.service;

import io.binghe.rpc.spi.annotation.SPI;

/**
 * @author You Chuande
 */
@SPI("spiService")
public interface SPIService {
    String hello(String name);
}
