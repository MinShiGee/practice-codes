package com.minshigee.authserver.domains.auth;

import com.minshigee.authserver.daos.entities.LocalAccount;
import com.minshigee.authserver.dependencies.reswrapper.ResponseFactory;
import com.minshigee.authserver.dependencies.reswrapper.WrappedResponse;
import com.minshigee.authserver.domains.auth.entities.LoginInfo;
import com.minshigee.authserver.domains.auth.interfaces.AuthService;
import io.r2dbc.spi.Wrapped;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public Mono<ResponseEntity<WrappedResponse<LoginInfo>>> signUpAccount(@RequestBody LocalAccount.SignupRequestDTO reqBody) {
        return authService.registerAccount(reqBody)
                        .map(loginInfo ->
                            ResponseFactory.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(loginInfo)
                            );
    }

    @PostMapping("/signin")
    public Mono<LocalAccount.SigninResponse> signInAccount(@RequestBody LocalAccount.SigninRequestDTO reqBody) {
        return null;
    }

}
