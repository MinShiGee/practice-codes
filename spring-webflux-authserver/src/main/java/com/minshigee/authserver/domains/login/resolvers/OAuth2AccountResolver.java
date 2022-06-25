package com.minshigee.authserver.domains.login.resolvers;

import com.minshigee.authserver.cores.exception.ErrorCode;
import com.minshigee.authserver.daos.entities.OAuth2Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class OAuth2AccountResolver {

    public OAuth2Account resolveOAuth2Account(Authentication authentication) {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String providerId = token.getAuthorizedClientRegistrationId();
        if("GOOGLE".equalsIgnoreCase(providerId)){
            return buildGoogleUser((OidcUser) authentication.getPrincipal());
        }
        throw ErrorCode.NO_AVAILABLE_PROVIDER_LOGIN.build();
    }

    protected OAuth2Account buildGoogleUser(OidcUser user) {
        return OAuth2Account.builder()
                .code(String.valueOf(String.valueOf(user.getClaims().get("sub"))))
                .userEmail(String.valueOf(user.getClaims().get("email")))
                .provider("GOOGLE")
                .build();
    }

}
