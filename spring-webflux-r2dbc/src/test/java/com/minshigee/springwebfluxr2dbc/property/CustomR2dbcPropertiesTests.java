package com.minshigee.springwebfluxr2dbc.property;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.logging.Logger;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:secret.properties")
@EnableConfigurationProperties(CustomR2dbcProperties.class)
public class CustomR2dbcPropertiesTests {
    @Autowired
    private CustomR2dbcProperties customR2dbcProperties;

    @Value("${spring.datasource.user.driver}")
    private String driver;
    @Value("${spring.datasource.user.name}")
    private String database;
    @Value("${spring.datasource.user.url}")
    private String host;
    @Value("${spring.datasource.user.port}")
    private Integer port;
    @Value("${spring.datasource.user.username}")
    private String user;
    @Value("${spring.datasource.user.password}")
    private String password;

    @Test
    public void checkCustomR2dbcProperties() {
        Assertions.assertEquals(customR2dbcProperties.getDriver(), driver);
        Assertions.assertEquals(customR2dbcProperties.getName(), database);
        Assertions.assertEquals(customR2dbcProperties.getUrl(), host);
        Assertions.assertEquals(customR2dbcProperties.getPort() , port);
        Assertions.assertEquals(customR2dbcProperties.getUsername(), user);
        Assertions.assertEquals(customR2dbcProperties.getPassword(), password);
    }

}
