package com.minshigee.authserver.cores.r2dbc.annotations;

import com.minshigee.authserver.cores.r2dbc.R2dbcConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:secret.properties") // Property 정보는 secret.properties에 저장되어있음.
@DataR2dbcTest(excludeAutoConfiguration = R2dbcAutoConfiguration.class) // Application Context에 property 2개가 잡히는 문제를 해결하기 위함.
@EnableR2dbcRepositories(basePackages = {"com.minshigee.authserver.*"}) // R2dbc Repo를 Test에 사용하기 위해 해줌
@EnableConfigurationProperties()
@ContextConfiguration()
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CustomDataR2dbcTest {
    Class<?>[] classes() default {R2dbcConfig.class};
    Class<?>[] value() default {};
}
