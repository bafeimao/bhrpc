package io.binghe.rpc.test.spi;

import io.binghe.rpc.spi.loader.ExtensionLoader;
import io.binghe.rpc.test.spi.service.SPIService;
import org.junit.Test;

/**
 * @author You Chuande
 */
public class SPITest {
    @Test
    public void testSpiLoader() {
        SPIService spiService = ExtensionLoader.getExtension(SPIService.class, "spiService");
        String result = spiService.hello("binghe");
        System.out.println(result);
    }
}
