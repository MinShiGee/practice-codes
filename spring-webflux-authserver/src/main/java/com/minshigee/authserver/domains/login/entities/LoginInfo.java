package com.minshigee.authserver.domains.login.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginInfo {
    Long authInfoId;
    String provider;
    Long accountId;
}
