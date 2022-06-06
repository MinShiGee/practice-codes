package com.minshigee.authserver.domains.daos.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
public class UserTokens {
    String refreshToken;
    String accessToken;
}
