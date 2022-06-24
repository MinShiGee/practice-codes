package com.minshigee.authserver.cores.security.oauth2.entities;

import com.minshigee.authserver.domains.daos.entities.AuthInfo;

public interface CustomAuthInfo {
    public AuthInfo extractAuthInfo();
}
