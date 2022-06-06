package com.minshigee.authserver.cores.r2dbc.properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:secret.properties")
@EnableConfigurationProperties(CustomR2dbcProperties.class)
public class CustomR2dbcPropertiesTest {
    @Autowired
    CustomR2dbcProperties customR2dbcProperties;

    @Test
    public void testCustomR2dbcPropertiesValue() {
        Assertions.assertNotNull(customR2dbcProperties);
    }

}
