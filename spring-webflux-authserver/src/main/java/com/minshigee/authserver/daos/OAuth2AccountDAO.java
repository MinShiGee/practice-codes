package com.minshigee.authserver.daos;

import com.minshigee.authserver.daos.entities.OAuth2Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface OAuth2AccountDAO extends ReactiveCrudRepository<OAuth2Account, Long> {
    Mono<OAuth2Account> findOAuth2AccountByUserEmail(String email);
}
