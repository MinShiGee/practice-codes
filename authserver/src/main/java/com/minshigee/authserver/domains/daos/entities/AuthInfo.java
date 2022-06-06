package com.minshigee.authserver.domains.daos.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;
import java.time.LocalDate;

@Table("authinfo")
@Data
@Builder
public class AuthInfo {
    @Id
    Long id;

    @Column("user_name")
    String userName;

    @Column("user_email")
    String userEmail;

    @Column("user_password")
    String userPassword;

    @Column("non_expired")
    @Builder.Default
    Boolean nonExpired = true;

    @Column("non_locked")
    @Builder.Default
    Boolean nonLocked = true;

    @Column("created_date")
    @Builder.Default
    Date createdDate = Date.valueOf(LocalDate.now());

    @Column("authorities")
    @Builder.Default
    String authorities = "ROLE_GUEST";

    @Column("provider")
    @Builder.Default
    String provider = "LOCAL";

    @Column("enable")
    @Builder.Default
    Boolean enable = true;

    @Builder
    @Data
    public static class Request {
        String userEmail;
        String userName;
        String userPassword;
    }

    @Builder
    @Data
    public static class Response {

    }
}
