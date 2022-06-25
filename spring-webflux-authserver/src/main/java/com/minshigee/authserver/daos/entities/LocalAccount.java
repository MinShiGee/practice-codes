package com.minshigee.authserver.daos.entities;

import com.minshigee.authserver.daos.entities.interfaces.IAccount;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("tblLocalAccounts")
@Builder
public class LocalAccount implements IAccount {
    @Id
    Long id;
    @Column("user_email")
    String userEmail;
    @Column("user_password")
    String userPassword;
    @Column("fk_authinfoes")
    Long fk_AuthInfoes;

    @Override
    public String getEmail() {
        return getUserEmail();
    }

    @Override
    public String getProvider() {
        return "LOCAL";
    }

    @Override
    public Long getAuthInfoCode() {
        return getFk_AuthInfoes();
    }
}