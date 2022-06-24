package com.minshigee.authserver.domains.authinfo;

import com.minshigee.authserver.domains.authinfo.interfaces.AuthInfoService;
import com.minshigee.authserver.domains.daos.entities.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthInfoController {

    private final AuthInfoService authInfoService;

}
