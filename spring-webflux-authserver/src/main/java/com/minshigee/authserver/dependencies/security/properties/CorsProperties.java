package com.minshigee.authserver.dependencies.security.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@Primary
@ToString
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.client.cors")
@PropertySource(value = "secret.properties")
@ConstructorBinding
public class CorsProperties {
    String[] url;
}