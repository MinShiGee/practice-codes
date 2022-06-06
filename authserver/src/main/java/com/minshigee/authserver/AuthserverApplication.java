package com.minshigee.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class AuthserverApplication {

    private static final String PROPERTIES = "spring.config.location=" +
            "classpath:/application.properties" +
            ",classpath:/secret.properties";

    public static void main(String[] args) {
        new SpringApplicationBuilder(AuthserverApplication.class)
                .properties(PROPERTIES)
                .run(args);
    }
}
