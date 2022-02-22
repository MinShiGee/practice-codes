package com.minshigee.springwebfluxr2dbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan

public class SpringWebfluxR2dbcApplication {
    private static final String PROPERTIES = "spring.config.location=" +
            "classpath:/application.properties" +
            ",classpath:/secret.properties";
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringWebfluxR2dbcApplication.class)
                .properties(PROPERTIES).run(args);
    }

}
