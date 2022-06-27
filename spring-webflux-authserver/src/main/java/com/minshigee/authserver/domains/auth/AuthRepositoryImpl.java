package com.minshigee.authserver.domains.auth;

import com.minshigee.authserver.daos.AuthInfoDAO;
import com.minshigee.authserver.daos.LocalAccountDAO;
import com.minshigee.authserver.daos.OAuth2AccountDAO;
import com.minshigee.authserver.daos.entities.AuthInfo;
import com.minshigee.authserver.daos.entities.LocalAccount;
import com.minshigee.authserver.daos.entities.OAuth2Account;
import com.minshigee.authserver.daos.entities.interfaces.IAccount;
import com.minshigee.authserver.domains.auth.interfaces.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class AuthRepositoryImpl implements AuthRepository {

    private final LocalAccountDAO localAccountDAO;
    private final OAuth2AccountDAO oAuth2AccountDAO;
    private final AuthInfoDAO authInfoDAO;
    private final Duration timeOutDuration = Duration.ofSeconds(3L);

    @Override
    @Transactional
    public Mono<OAuth2Account> createAccount(OAuth2Account account) {
        return oAuth2AccountDAO.save(account)
                .timeout(timeOutDuration);
    }

    @Override
    @Transactional
    public Mono<LocalAccount> createAccount(LocalAccount account) {
        return localAccountDAO.save(account)
                .timeout(timeOutDuration);
    }

    @Override
    @Transactional
    public Mono<AuthInfo> createAuthInfo(OAuth2Account account) {
        return createAuthInfo((IAccount) account);
    }

    @Override
    @Transactional
    public Mono<AuthInfo> createAuthInfo(LocalAccount account) {
        return createAuthInfo((IAccount) account);
    }

    @Override
    @Transactional
    public Mono<AuthInfo> createAuthInfo(IAccount account) {
        return authInfoDAO.save(
                AuthInfo.builder()
                        .userEmail(account.getEmail())
                        .build()
        ).timeout(timeOutDuration);
    }

    @Override
    public Mono<OAuth2Account> getOAuth2Account(Long id) {
        return oAuth2AccountDAO.findById(id)
                .timeout(timeOutDuration);
    }

    @Override
    public Mono<OAuth2Account> getOAuth2Account(String email) {
        return oAuth2AccountDAO.findOAuth2AccountByUserEmail(email)
                .timeout(timeOutDuration);
    }

    @Override
    public Mono<LocalAccount> getLocalAccount(Long id) {
        return localAccountDAO.findById(id)
                .timeout(timeOutDuration);
    }

    @Override
    public Mono<LocalAccount> getLocalAccount(String email) {
        return localAccountDAO.findLocalAccountByUserEmail(email);
    }

    @Override
    public Mono<AuthInfo> getAuthInfo(Long id) {
        return authInfoDAO.findById(id)
                .timeout(timeOutDuration);
    }

    @Override
    public Mono<AuthInfo> getAuthInfo(String email) {
        return authInfoDAO.findAuthInfoByUserEmail(email);
    }

    @Override
    @Transactional
    public Mono<AuthInfo> updateAuthInfo(AuthInfo authInfo) {
        return null;
    }
}
