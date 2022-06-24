package com.minshigee.authserver.domains.authinfo;

import com.minshigee.authserver.domains.authinfo.interfaces.AuthInfoRepository;
import com.minshigee.authserver.domains.authinfo.interfaces.AuthInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthInfoServiceImpl implements AuthInfoService {
    private final AuthInfoRepository authInfoRepositoryImpl;
}
