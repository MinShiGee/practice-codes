package com.minshigee.authserver.domains.authinfo.interfaces;

import com.minshigee.authserver.daos.entities.AuthInfo;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public interface AuthInfoRepository {

    Mono<AuthInfo> getAuthInfoById(Long id);
    Mono<AuthInfo> getAuthInfoByUserEmail(String userEmail);
    @Transactional
    Mono<AuthInfo> createAuthInfo(AuthInfo.Request authInfoDTO);
    @Transactional
    Mono<AuthInfo> updateAuthInfo(AuthInfo.Request authInfoDTO);
    @Transactional
    Mono<Void> deleteAuthInfo(Long id);
    @Transactional
    Mono<Void> deleteAuthInfo(String userEmail);
}
