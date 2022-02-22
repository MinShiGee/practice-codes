package com.minshigee.springwebfluxr2dbc.config;

import com.minshigee.springwebfluxr2dbc.property.CustomR2dbcProperties;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import io.r2dbc.spi.Option;

import java.time.Duration;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Configuration
@EnableR2dbcRepositories
public class R2dbcConfig extends AbstractR2dbcConfiguration {

    private final CustomR2dbcProperties r2dbcProperties;

    @Autowired
    public R2dbcConfig(CustomR2dbcProperties r2dbcProperties) {
        this.r2dbcProperties = r2dbcProperties;
    }

    @Override
    public ConnectionFactory connectionFactory() {
        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(DRIVER, r2dbcProperties.getName())
                .option(HOST, r2dbcProperties.getUrl())
                .option(USER, r2dbcProperties.getUsername())
                .option(PORT, r2dbcProperties.getPort())
                .option(PASSWORD, r2dbcProperties.getPassword())
                .option(DATABASE, r2dbcProperties.getName())
                .option(CONNECT_TIMEOUT, Duration.ofSeconds(3))
                .option(Option.valueOf("socketTimeout"), Duration.ofSeconds(4))
                .option(Option.valueOf("tcpKeepAlive"), true)
                .option(Option.valueOf("tcpNoDelay"), true)
                .build();
        return ConnectionFactories.get(options);
    }
}
