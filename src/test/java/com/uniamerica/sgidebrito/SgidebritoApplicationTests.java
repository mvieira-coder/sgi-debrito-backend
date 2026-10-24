package com.uniamerica.sgidebrito;

import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SgidebritoApplicationTests {
    @Test
    void contextLoads() {
        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(SgidebritoApplication.class)
                .profiles("test").run("--server.port=0")) {
            assertNotNull(context.getBean(SgidebritoApplication.class));
        }
    }
}
