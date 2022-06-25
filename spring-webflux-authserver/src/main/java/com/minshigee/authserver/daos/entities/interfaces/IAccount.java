package com.minshigee.authserver.daos.entities.interfaces;

public interface IAccount {
    String getEmail();
    String getProvider();
    Long getAuthInfoCode();
    void setFk_AuthInfoes(Long id);
}
