package com.minshigee.authserver.domains.main;

import com.minshigee.authserver.dependencies.reswrapper.ResponseFactory;
import com.minshigee.authserver.dependencies.reswrapper.WrappedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class MainController {

    @GetMapping("")
    public Mono<ResponseEntity<WrappedResponse<String>>> getHelloWorld() {
        return Mono.just(
                ResponseFactory.ok().body("Hello World!")
        );
    }
}
