package com.minshigee.authserver.dependencies.r2dbc.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@Primary
@ToString
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.datasource.user")
@PropertySource(value = "secret.properties")
@ConstructorBinding
public class CustomR2dbcProperties extends R2dbcProperties {
    private String driver;
    private Integer port;
    private String protocol;
    @Qualifier("maxsize")
    public Integer maxSize;
    @Qualifier("initialsize")
    public Integer initialSize;
    @Qualifier("maxidletime")
    public long maxIdleTime;
    @Qualifier("maxcreateconnectiontime")
    public long maxCreateConnectionTime;
    @Qualifier("maxlife")
    public long maxLife;
}