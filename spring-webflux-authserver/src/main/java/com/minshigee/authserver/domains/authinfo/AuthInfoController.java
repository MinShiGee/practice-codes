package com.minshigee.authserver.domains.authinfo;

import com.minshigee.authserver.domains.authinfo.interfaces.AuthInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthInfoController {

    private final AuthInfoService authInfoService;

}
