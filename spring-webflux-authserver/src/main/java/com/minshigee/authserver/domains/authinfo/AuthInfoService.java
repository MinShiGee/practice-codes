package com.minshigee.authserver.domains.authinfo;

import com.minshigee.authserver.cores.exception.ErrorCode;
import com.minshigee.authserver.domains.daos.AuthInfoDAO;
import com.minshigee.authserver.domains.daos.entities.AuthInfo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthInfoService {

    final AuthInfoDAO authInfoDAO;
    private final Duration timeOutSecond = Duration.ofSeconds(3L);

    public Mono<AuthInfo> registerAuthInfo(AuthInfo.Request authInfoDTO) {
        return createAuthInfo(authInfoDTO);
    }

    protected Mono<AuthInfo> getAuthInfoById(Long id) {
        return authInfoDAO.findById(id)
                .timeout(timeOutSecond);
    }

    protected Mono<AuthInfo> getAuthInfoByUserEmail(String userEmail) {
        return authInfoDAO.findAuthInfoByUserEmail(userEmail)
                .timeout(timeOutSecond);
    }

    @Transactional
    protected Mono<AuthInfo> createAuthInfo(AuthInfo.Request authInfoDTO) {
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
    protected Mono<AuthInfo> updateAuthInfo(AuthInfo.Request authInfoDTO) {
        return getAuthInfoByUserEmail(authInfoDTO.getUserEmail())
                .switchIfEmpty(Mono.error(ErrorCode.AUTHINFO_NOT_FOUND.build()))
                .doOnNext(authInfo -> {
                    if(authInfoDTO.getUserEmail() != null)
                        authInfo.setUserEmail(authInfoDTO.getUserEmail());
                    if(authInfoDTO.getUserName() != null)
                        authInfo.setUserName(authInfoDTO.getUserName());
                    if(authInfoDTO.getUserPassword() != null)
                        authInfo.setUserPassword(authInfoDTO.getUserPassword());
                })
                .doOnNext(authInfoDAO::save)
                .timeout(timeOutSecond);
    }

    @Transactional
    protected Mono<Void> deleteAuthInfo(Long id) {
        return authInfoDAO.deleteById(id)
                .timeout(timeOutSecond);
    }

}
