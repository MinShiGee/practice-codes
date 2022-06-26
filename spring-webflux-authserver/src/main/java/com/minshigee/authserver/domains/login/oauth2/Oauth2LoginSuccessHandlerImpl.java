package com.minshigee.authserver.domains.login.oauth2;

import com.minshigee.authserver.dependencies.exception.ErrorCode;
import com.minshigee.authserver.domains.login.interfaces.LoginService;
import com.minshigee.authserver.domains.login.resolvers.JwtResolver;
import com.minshigee.authserver.domains.login.resolvers.OAuth2AccountResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class Oauth2LoginSuccessHandlerImpl extends RedirectServerAuthenticationSuccessHandler {

    private final ServerRedirectStrategy serverRedirectStrategy = new DefaultServerRedirectStrategy();
    private final LoginService loginService;
    private final OAuth2AccountResolver oAuth2AccountResolver;
    private final JwtResolver jwtResolver;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        try {
            return loginService.registerAccount(oAuth2AccountResolver.resolveOAuth2Account(authentication))
                    .doOnNext(loginInfo -> {
                        String token = jwtResolver.generateToken(loginInfo);
                        jwtResolver.addCookieAccessTokenToResponse(webFilterExchange.getExchange().getResponse(), token);
            }).flatMap(authInfo -> serverRedirectStrategy.sendRedirect(webFilterExchange.getExchange(), URI.create("")));
        }
        catch (Exception e) {
            throw ErrorCode.CANT_LOGIN_OAUTH2.build();
        }
    }

}
