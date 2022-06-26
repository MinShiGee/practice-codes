package com.minshigee.authserver.domains.login.oauth2;

import com.minshigee.authserver.dependencies.exception.ErrorCode;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class OidcUserServiceImpl implements ReactiveOAuth2UserService<OidcUserRequest, OidcUser> {

    private final OidcReactiveOAuth2UserService delegate = new OidcReactiveOAuth2UserService();

    @Override
    public Mono<OidcUser> loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        return delegate.loadUser(userRequest).doOnNext(user -> {
            if(!"GOOGLE".equals(userRequest.getClientRegistration().getRegistrationId()))
                throw ErrorCode.NO_AVAILABLE_PROVIDER_LOGIN.build();
        });
    }
}
