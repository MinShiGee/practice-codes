package com.minshigee.authserver.domains.auth;

import com.minshigee.authserver.dependencies.exception.ErrorCode;
import com.minshigee.authserver.daos.entities.AuthInfo;
import com.minshigee.authserver.daos.entities.LocalAccount;
import com.minshigee.authserver.daos.entities.OAuth2Account;
import com.minshigee.authserver.daos.entities.interfaces.IAccount;
import com.minshigee.authserver.domains.auth.entities.LoginInfo;
import com.minshigee.authserver.domains.auth.interfaces.AuthRepository;
import com.minshigee.authserver.domains.auth.interfaces.AuthService;
import com.minshigee.authserver.domains.auth.resolvers.JwtResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtResolver jwtResolver;

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

    @Override
    public Mono<LoginInfo> registerAccount(LocalAccount.SignupRequestDTO reqDto) {
        return registerAccount(
                LocalAccount.builder()
                        .userEmail(reqDto.getUserEmail())
                        .userPassword(passwordEncoder.encode(reqDto.getUserPassword()))
                        .build()
        );
    }

    @Override
    public Mono<LoginInfo> signinLocalAccount(LocalAccount.SigninRequestDTO reqDto) {
        return null;
    }

    @Transactional
    protected Mono<AuthInfo> getAuthInfoAutoGenerate(IAccount account) {
        return authRepository.getAuthInfo(account.getEmail())
                .switchIfEmpty(authRepository.createAuthInfo(account));
    }

    @Transactional
    protected Mono<LocalAccount> getAccountAutoGenerate(LocalAccount account) {
        return authRepository.getLocalAccount(account.getUserEmail())
                .doOnNext(this::throwIfAccountExist)
                .switchIfEmpty(authRepository.createAccount(account));
    }

    @Transactional
    protected Mono<OAuth2Account> getAccountAutoGenerate(OAuth2Account account) {
        return authRepository.getOAuth2Account(account.getUserEmail())
                .switchIfEmpty(authRepository.createAccount(account));
    }

    private void throwIfAccountExist(IAccount account) {
        if(account != null)
            throw ErrorCode.ALEADY_EXIST_LOCALACCOUNT.build();
    }
}
