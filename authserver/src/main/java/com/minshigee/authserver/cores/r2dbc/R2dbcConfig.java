package com.minshigee.authserver.cores.r2dbc;

import com.minshigee.authserver.cores.r2dbc.properties.CustomR2dbcProperties;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Duration;

import static io.r2dbc.pool.PoolingConnectionFactoryProvider.*;
import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Primary
@Configuration
@EnableR2dbcRepositories
@EnableConfigurationProperties(value = {
        CustomR2dbcProperties.class
})
public class R2dbcConfig extends AbstractR2dbcConfiguration {

    private final CustomR2dbcProperties customR2dbcProperties;

    public R2dbcConfig(CustomR2dbcProperties customR2dbcProperties) {
        this.customR2dbcProperties = customR2dbcProperties;
    }

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        //System.err.println(customR2dbcProperties);
        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(DRIVER, customR2dbcProperties.getDriver())
                .option(PROTOCOL, customR2dbcProperties.getProtocol())
                .option(HOST, customR2dbcProperties.getUrl())
                .option(USER, customR2dbcProperties.getUsername())
                .option(PORT, customR2dbcProperties.getPort())
                .option(PASSWORD, customR2dbcProperties.getPassword())
                .option(DATABASE, customR2dbcProperties.getName())
                .option(CONNECT_TIMEOUT, Duration.ofSeconds(3))
                .option(MAX_SIZE, customR2dbcProperties.maxSize)
                .option(INITIAL_SIZE, customR2dbcProperties.initialSize)
                .option(MAX_IDLE_TIME, Duration.ofSeconds(customR2dbcProperties.maxIdleTime))
                .option(MAX_CREATE_CONNECTION_TIME, Duration.ofSeconds(customR2dbcProperties.maxCreateConnectionTime))
                //.option(MAX_LIFE_TIME, Duration.ofMinutes(customR2dbcProperties.maxLife))
                .build();

        return ConnectionFactories.get(options);
    }

    @Bean
    @ExceptionHandler(Exception.class)
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new CustomConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(
                new ResourceDatabasePopulator(
                        new ClassPathResource("sql/create_data_db.sql")
                )
        );
        return initializer;
    }

    class CustomConnectionFactoryInitializer extends ConnectionFactoryInitializer {
        @Override
        public void afterPropertiesSet() {
            try {
                super.afterPropertiesSet();
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}