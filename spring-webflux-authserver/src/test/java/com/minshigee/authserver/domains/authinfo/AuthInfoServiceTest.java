package com.minshigee.authserver.domains.authinfo;

import com.minshigee.authserver.cores.r2dbc.annotations.CustomDataR2dbcTest;
import com.minshigee.authserver.cores.r2dbc.R2dbcConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@CustomDataR2dbcTest(
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

    @Test
    public void createAuthInfoTest() {

    }

    @Test
    public void getAuthInfoTest() {

    }

    @Test
    public void updateAuthInfoTest() {

    }

    @Test
    public void deleteAuthInfoTest() {

    }

}
