package com.minshigee.authserver.domains.daos;

import com.minshigee.authserver.domains.daos.entities.AuthInfo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AuthInfoDAO extends ReactiveCrudRepository<AuthInfo, Long> {
    public Mono<AuthInfo> findAuthInfoByUserEmail(String userEmail);
}
