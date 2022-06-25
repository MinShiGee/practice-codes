package com.minshigee.authserver.domains.login.entities;

import org.springframework.data.r2dbc.convert.EnumWriteSupport;

public enum Role {
    ROLE_GUEST,
    ROLE_USER,
    ROLE_ADMIN
}
class RoleConverter extends EnumWriteSupport<Role> {
}