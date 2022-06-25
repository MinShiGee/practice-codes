package com.minshigee.authserver.cores.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ErrorCode {

    //OAuth2
    NO_AVAILABLE_PROVIDER_LOGIN(new GlobalException(HttpStatus.BAD_REQUEST, "지원하지 않는 방식의 로그인입니다.")),
    CANT_LOGIN_OAUTH2(new GlobalException(HttpStatus.BAD_REQUEST, "외부 로그인을 진행할 수 없습니다.")),
    CANT_SIGN_UP_OAUTH2(new GlobalException(HttpStatus.BAD_REQUEST, "OAuth2 사용자 정보를 생성하는데 실패했습니다.")),

    //JWT TOKEN ERROR
    ACCESS_TOKEN_ERROR(new GlobalException(HttpStatus.BAD_REQUEST, "액세스 토근이 유효하지 않습니다.")),
    REFRESH_TOKEN_ERROR(new GlobalException(HttpStatus.BAD_REQUEST, "리프레시 토근이 유효하지 않습니다.")),
    ACCESS_TOKEN_EXPRIRED(new GlobalException(HttpStatus.BAD_REQUEST, "만료된 엑세스 토큰입니다.")),
    REFRESH_TOKEN_EXPIRED(new GlobalException(HttpStatus.BAD_REQUEST, "만료된 리프레시 토큰입니다.")),
    LEAK_AUTHORITY(new GlobalException(HttpStatus.BAD_REQUEST, "접근할 수 없는 권한입니다.")),

    AUTHSERVER_PROBLEM(new GlobalException(HttpStatus.BAD_REQUEST, "인증서버에 문제가 발생했습니다.")),

    //AUTHINFO ERROR
    AUTHINFO_NOT_FOUND(new GlobalException(HttpStatus.NOT_FOUND, "사용자 인증정보를 찾을 수 없습니다.")),
    AUTHINFO_ERROR(new GlobalException(HttpStatus.BAD_REQUEST, "인증 기능에 문제가 발생했습니다.")),
    AUTHINFO_TIMEOUT(new GlobalException(HttpStatus.REQUEST_TIMEOUT, "인증정보를 불러오는데 긴 시간이 걸립니다.")),

    //END
    ERRORCODE_END(new GlobalException(HttpStatus.BAD_REQUEST,"404"));
    private final GlobalException globalException;

    public GlobalException build() {
        return globalException;
    }
}