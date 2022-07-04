package com.minshigee.authserver.domains.auth.entities;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoginInfo {
    Long authInfoId;
    String provider;
    Long accountId;
    List<Role> authorities;
}
