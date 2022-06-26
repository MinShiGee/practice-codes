package com.minshigee.authserver.domains.login;

import com.minshigee.authserver.dependencies.reswrapper.BaseResponse;
import com.minshigee.authserver.daos.entities.LocalAccount;
import com.minshigee.authserver.domains.login.interfaces.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/signup")
    public Mono<ResponseEntity> signUpAccount(@RequestBody LocalAccount.SignupRequestDTO reqBody) {
        return BaseResponse.builder()
                .body(loginService.registerAccount(reqBody))
                .status(HttpStatus.OK)
                .build().toMonoEntity();
    }

    @PostMapping("/signin")
    public Mono<LocalAccount.SigninResponse> signInAccount(@RequestBody LocalAccount.SigninRequestDTO reqBody) {
        return null;
    }

}
