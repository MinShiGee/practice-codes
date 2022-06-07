package com.minshigee.authserver.domains.authinfo;

import com.minshigee.authserver.cores.r2dbc.annotations.CustomDataR2dbcTest;
import com.minshigee.authserver.cores.r2dbc.R2dbcConfig;
import com.minshigee.authserver.cores.r2dbc.properties.CustomR2dbcProperties;
import com.minshigee.authserver.domains.daos.entities.AuthInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.time.Duration;

@CustomDataR2dbcTest(
        value = {CustomR2dbcProperties.class},
        classes = {
                R2dbcConfig.class,
                AuthInfoService.class
        }
)
public class AuthInfoServiceTest {

    @Autowired
    public AuthInfoService authInfoService;

    @Test
    public void injectAuthInfoServiceTest() {
        Assertions.assertNotNull(authInfoService);
    }

    @Test
    public void crudAuthInfoTest() {
        createAuthInfoTest();
        getAuthInfoTest();
        updateAuthInfoTest();
        deleteAuthInfoTest();
    }

    public void createAuthInfoTest() {
        AuthInfo.Request reqDTO = getAuthInfoRequestDto();
        StepVerifier.create(authInfoService.createAuthInfo(reqDTO))
                .assertNext(Assertions::assertNotNull)
                .verifyComplete();
    }

    public void getAuthInfoTest() {
        AuthInfo.Request reqDTO = getAuthInfoRequestDto();
        StepVerifier.create(authInfoService.getAuthInfoByUserEmail(reqDTO.getUserEmail()))
                .assertNext(authInfo -> Assertions.assertEquals(authInfo.getUserName(),reqDTO.getUserName()))
                .verifyComplete();
    }

    public void updateAuthInfoTest() {
        AuthInfo.Request reqDTO = getAuthInfoRequestDto();
        reqDTO.setUserName("ttest");
        reqDTO.setUserPassword("ppassword");
        StepVerifier.create(authInfoService.updateAuthInfo(reqDTO))
                .assertNext(authInfo -> {
                    Assertions.assertEquals(authInfo.getUserName(),"ttest");
                    Assertions.assertEquals(authInfo.getUserPassword(),"ppassword");
                })
                .verifyComplete();
    }

    public void deleteAuthInfoTest() {
        AuthInfo.Request reqDTO = getAuthInfoRequestDto();
        StepVerifier.create(authInfoService.deleteAuthInfo(reqDTO.getUserEmail()))
                .verifyComplete();
    }

    private AuthInfo.Request getAuthInfoRequestDto() {
        return AuthInfo.Request.builder()
                .userName("TestName")
                .userEmail("Test@gmail.com")
                .userPassword("password")
                .build();
    }

}
