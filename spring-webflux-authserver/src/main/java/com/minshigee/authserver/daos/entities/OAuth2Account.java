package com.minshigee.authserver.daos.entities;

import com.minshigee.authserver.daos.entities.interfaces.IAccount;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("tblOAuth2Accounts")
@Builder
public class OAuth2Account implements IAccount {
    @Id
    Long id;
    @Column("code")
    String code;
    @Column("provider")
    String provider;
    @Column("user_email")
    String userEmail;
    @Column("fk_authinfoes")
    Long fk_AuthInfoes;

    @Override
    public String getEmail() {
        return getUserEmail();
    }

    @Override
    public Long getAuthInfoCode() {
        return getFk_AuthInfoes();
    }
}
