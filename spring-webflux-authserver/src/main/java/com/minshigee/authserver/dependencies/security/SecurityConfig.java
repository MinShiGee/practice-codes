package com.minshigee.authserver.dependencies.security;

import com.minshigee.authserver.domains.auth.filters.JwtAuthenticationFilter;
import com.minshigee.authserver.dependencies.security.properties.CorsProperties;
import com.minshigee.authserver.domains.auth.oauth2.Oauth2LoginSuccessHandlerImpl;
import com.minshigee.authserver.domains.auth.resolvers.JwtResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
@ComponentScan(basePackages = {
        "com.minshigee.authserver"
})
@EnableConfigurationProperties(value = {
        CorsProperties.class
})
public class SecurityConfig {

    private final CorsProperties corsProperties;
    private final JwtResolver jwtResolver;
    private final Oauth2LoginSuccessHandlerImpl oauth2LoginSuccessHandler;

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        //TODO Make Stateless
        http.securityContextRepository(NoOpServerSecurityContextRepository.getInstance());

        //TODO OAuth2Login
        http.oauth2Login(Customizer.withDefaults())
                .oauth2Login(oAuth2LoginSpec -> {
                    oAuth2LoginSpec.authenticationSuccessHandler(oauth2LoginSuccessHandler);
                });

        //TODO ADD CUSTOM Filter
        http.addFilterBefore(JwtAuthenticationFilter.builder().jwtResolver(jwtResolver).build(),
                SecurityWebFiltersOrder.OAUTH2_AUTHORIZATION_CODE);

        //TODO ETC
        http.csrf().disable();
        http.formLogin().disable();
        http.logout().disable();

        return http.authorizeExchange()
                .pathMatchers("/api", "/login/**", "/authorize/**", "/webjars/swagger-ui/**", "/v3/api-docs/**", "/api/signup", "/api/signin").permitAll()
                .pathMatchers("/admin/**").hasRole("ADMIN")
                .anyExchange().authenticated()
                .and().build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(corsProperties.getUrl()));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
