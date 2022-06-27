package com.minshigee.authserver.domains.auth.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginInfo {
    Long authInfoId;
    String provider;
    Long accountId;
}
