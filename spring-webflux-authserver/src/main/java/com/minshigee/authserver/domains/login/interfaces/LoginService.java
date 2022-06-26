package com.minshigee.authserver.domains.login.interfaces;

import com.minshigee.authserver.daos.entities.LocalAccount;
import com.minshigee.authserver.daos.entities.OAuth2Account;
import com.minshigee.authserver.domains.login.entities.LoginInfo;
import reactor.core.publisher.Mono;

public interface LoginService {
    Mono<LoginInfo> registerAccount(LocalAccount account);
    Mono<LoginInfo> registerAccount(OAuth2Account account);
    Mono<LoginInfo> registerAccount(LocalAccount.SignupRequestDTO reqDto);
    Mono<LoginInfo> signinLocalAccount(LocalAccount.SigninRequestDTO reqDto);
}
