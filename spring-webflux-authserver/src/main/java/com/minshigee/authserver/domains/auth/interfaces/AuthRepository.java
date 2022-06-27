package com.minshigee.authserver.domains.auth.interfaces;

import com.minshigee.authserver.daos.entities.AuthInfo;
import com.minshigee.authserver.daos.entities.LocalAccount;
import com.minshigee.authserver.daos.entities.OAuth2Account;
import com.minshigee.authserver.daos.entities.interfaces.IAccount;
import reactor.core.publisher.Mono;

public interface AuthRepository {
    Mono<OAuth2Account> createAccount(OAuth2Account account);
    Mono<LocalAccount> createAccount(LocalAccount account);

    Mono<AuthInfo> createAuthInfo(OAuth2Account account);
    Mono<AuthInfo> createAuthInfo(LocalAccount account);
    Mono<AuthInfo> createAuthInfo(IAccount account);

    Mono<OAuth2Account> getOAuth2Account(Long id);
    Mono<OAuth2Account> getOAuth2Account(String email);

    Mono<LocalAccount> getLocalAccount(Long id);
    Mono<LocalAccount> getLocalAccount(String email);

    Mono<AuthInfo> getAuthInfo(Long id);
    Mono<AuthInfo> getAuthInfo(String email);

    Mono<AuthInfo> updateAuthInfo(AuthInfo authInfo);
}
