package com.minshigee.springwebfluxr2dbc.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.PropertySource;

import java.beans.ConstructorProperties;

@ToString
@ConfigurationProperties(prefix = "spring.datasource.user")
@PropertySource(value = "secret.properties")
@ConstructorBinding
@Getter
@Setter
public class CustomR2dbcProperties extends R2dbcProperties {
    private String driver;
    private Integer port;
}
