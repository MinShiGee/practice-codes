package com.minshigee.authserver.domains.login;

import com.minshigee.authserver.daos.entities.AuthInfo;
import com.minshigee.authserver.daos.entities.LocalAccount;
import com.minshigee.authserver.daos.entities.OAuth2Account;
import com.minshigee.authserver.daos.entities.interfaces.IAccount;
import com.minshigee.authserver.domains.login.entities.LoginInfo;
import com.minshigee.authserver.domains.login.interfaces.LoginRepository;
import com.minshigee.authserver.domains.login.interfaces.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginRepository loginRepository;

    @Override
    @Transactional
    public Mono<LoginInfo> registerAccount(LocalAccount account) {
        LoginInfo loginInfo = LoginInfo.builder().build();
        return getAuthInfoAutoGenerate(account)
                .map(authInfo -> {
                    loginInfo.setAuthInfoId(authInfo.getId());
                    account.setFk_AuthInfoes(authInfo.getId());
                    return account;
                }).flatMap(this::getAccountAutoGenerate)
                .flatMap(accountt -> {
                    loginInfo.setProvider(accountt.getProvider());
                    loginInfo.setAccountId(accountt.getId());
                    return Mono.just(loginInfo);
                });
    }

    @Override
    @Transactional
    public Mono<LoginInfo> registerAccount(OAuth2Account account) {
        LoginInfo loginInfo = LoginInfo.builder().build();
        return getAuthInfoAutoGenerate(account)
                .map(authInfo -> {
                    loginInfo.setAuthInfoId(authInfo.getId());
                    account.setFk_AuthInfoes(authInfo.getId());
                    return account;
                }).flatMap(this::getAccountAutoGenerate)
                .flatMap(accountt -> {
                    loginInfo.setProvider(accountt.getProvider());
                    loginInfo.setAccountId(accountt.getId());
                    return Mono.just(loginInfo);
                });
    }

    @Transactional
    protected Mono<AuthInfo> getAuthInfoAutoGenerate(IAccount account) {
        return loginRepository.getAuthInfo(account.getEmail())
                .switchIfEmpty(loginRepository.createAuthInfo(account));
    }

    @Transactional
    protected Mono<LocalAccount> getAccountAutoGenerate(LocalAccount account) {
        return loginRepository.getLocalAccount(account.getUserEmail())
                .switchIfEmpty(loginRepository.createAccount(account));
    }

    @Transactional
    protected Mono<OAuth2Account> getAccountAutoGenerate(OAuth2Account account) {
        return loginRepository.getOAuth2Account(account.getUserEmail())
                .switchIfEmpty(loginRepository.createAccount(account));
    }

}
