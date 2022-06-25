package com.minshigee.authserver.daos;

import com.minshigee.authserver.daos.entities.AuthInfo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AuthInfoDAO extends ReactiveCrudRepository<AuthInfo, Long> {
    public Mono<AuthInfo> findAuthInfoByUserEmail(String userEmail);
    public Mono<Void> deleteAuthInfoByUserEmail(String userEmail);
}
