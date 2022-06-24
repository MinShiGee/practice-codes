package com.minshigee.authserver.domains.authinfo;

import com.minshigee.authserver.cores.r2dbc.annotations.CustomDataR2dbcTest;
import com.minshigee.authserver.cores.r2dbc.R2dbcConfig;
import com.minshigee.authserver.cores.r2dbc.properties.CustomR2dbcProperties;
import com.minshigee.authserver.domains.authinfo.interfaces.AuthInfoRepository;
import com.minshigee.authserver.domains.daos.entities.AuthInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

@CustomDataR2dbcTest(
        value = {CustomR2dbcProperties.class},
        classes = {
                R2dbcConfig.class,
                AuthInfoRepositoryImpl.class
        }
)
public class AuthInfoRepositoryImplTest {

    @Autowired
    public AuthInfoRepository authInfoRepositoryImpl;

    @Test
    public void injectAuthInfoServiceTest() {
        Assertions.assertNotNull(authInfoRepositoryImpl);
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
        StepVerifier.create(authInfoRepositoryImpl.createAuthInfo(reqDTO))
                .assertNext(Assertions::assertNotNull)
                .verifyComplete();
    }

    public void getAuthInfoTest() {
        AuthInfo.Request reqDTO = getAuthInfoRequestDto();
        StepVerifier.create(authInfoRepositoryImpl.getAuthInfoByUserEmail(reqDTO.getUserEmail()))
                .assertNext(authInfo -> Assertions.assertEquals(authInfo.getUserName(),reqDTO.getUserName()))
                .verifyComplete();
    }

    public void updateAuthInfoTest() {
        AuthInfo.Request reqDTO = getAuthInfoRequestDto();
        reqDTO.setUserName("ttest");
        reqDTO.setUserPassword("ppassword");
        StepVerifier.create(authInfoRepositoryImpl.updateAuthInfo(reqDTO))
                .assertNext(authInfo -> {
                    Assertions.assertEquals(authInfo.getUserName(),"ttest");
                    Assertions.assertEquals(authInfo.getUserPassword(),"ppassword");
                })
                .verifyComplete();
    }

    public void deleteAuthInfoTest() {
        AuthInfo.Request reqDTO = getAuthInfoRequestDto();
        StepVerifier.create(authInfoRepositoryImpl.deleteAuthInfo(reqDTO.getUserEmail()))
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
