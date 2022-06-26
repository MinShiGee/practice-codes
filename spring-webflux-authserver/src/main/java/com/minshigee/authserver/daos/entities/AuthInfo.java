package com.minshigee.authserver.daos.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.TimeZone;

@Table("tblAuthInfoes")
@Data
@Builder
public class AuthInfo {
    @Id
    Long id;

    @Column("user_email")
    String userEmail;

    @Column("non_expired")
    @Builder.Default
    Boolean nonExpired = true;

    @Column("non_locked")
    @Builder.Default
    Boolean nonLocked = true;

    @Column("created_date")
    @Builder.Default
    LocalDate createdDate = LocalDate.now(ZoneId.of("Asia/Seoul"));

    @Column("authorities")
    @Builder.Default
    String authorities = "ROLE_GUEST";

    @Column("enable")
    @Builder.Default
    Boolean enable = false;

}
