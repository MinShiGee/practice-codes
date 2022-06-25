package com.minshigee.authserver.domains.authinfo;

import com.minshigee.authserver.cores.exception.ErrorCode;
import com.minshigee.authserver.domains.authinfo.interfaces.AuthInfoRepository;
import com.minshigee.authserver.daos.AuthInfoDAO;
import com.minshigee.authserver.daos.entities.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class AuthInfoRepositoryImpl implements AuthInfoRepository {

    final AuthInfoDAO authInfoDAO;
    private final Duration timeOutSecond = Duration.ofSeconds(3L);

    public Mono<AuthInfo> getAuthInfoById(Long id) {
        return authInfoDAO.findById(id)
                .timeout(timeOutSecond);
    }

    public Mono<AuthInfo> getAuthInfoByUserEmail(String userEmail) {
        return authInfoDAO.findAuthInfoByUserEmail(userEmail)
                .timeout(timeOutSecond);
    }

    @Transactional
    public Mono<AuthInfo> createAuthInfo(AuthInfo.Request authInfoDTO) {
        return getAuthInfoByUserEmail(authInfoDTO.getUserEmail())
                .switchIfEmpty(Mono.just(AuthInfo.builder()
                        .userName(authInfoDTO.getUserName())
                        .userEmail(authInfoDTO.getUserEmail())
                        .userPassword(authInfoDTO.getUserPassword())
                        .build()))
                .flatMap(authInfoDAO::save)
                .timeout(timeOutSecond);
    }

    @Transactional
    public Mono<AuthInfo> updateAuthInfo(AuthInfo.Request authInfoDTO) {
        return getAuthInfoByUserEmail(authInfoDTO.getUserEmail())
                .switchIfEmpty(Mono.error(ErrorCode.AUTHINFO_NOT_FOUND.build()))
                .doOnNext(authInfo -> {
                    if(authInfoDTO.getUserName() != null)
                        authInfo.setUserName(authInfoDTO.getUserName());
                    if(authInfoDTO.getUserPassword() != null)
                        authInfo.setUserPassword(authInfoDTO.getUserPassword());
                })
                .flatMap(authInfoDAO::save)
                .timeout(timeOutSecond);
    }

    @Transactional
    public Mono<Void> deleteAuthInfo(Long id) {
        return authInfoDAO.deleteById(id)
                .timeout(timeOutSecond);
    }
    @Transactional
    public Mono<Void> deleteAuthInfo(String userEmail) {
        return authInfoDAO.deleteAuthInfoByUserEmail(userEmail)
                .timeout(timeOutSecond);
    }

}
