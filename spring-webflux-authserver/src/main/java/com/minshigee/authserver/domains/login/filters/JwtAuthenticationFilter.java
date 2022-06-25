package com.minshigee.authserver.domains.login.filters;

import com.minshigee.authserver.domains.login.resolvers.JwtResolver;
import lombok.Builder;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Builder
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtResolver jwtResolver;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (jwtResolver.isAppropriateRequestForFilter(request)) {
            try {
                String token = jwtResolver.resolveToken(request);
                Authentication authentication = jwtResolver.getAuthentication(token);
                return chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
            } catch (Exception e) {
            }
        }

        return chain.filter(exchange);
    }

}
