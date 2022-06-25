package com.minshigee.authserver.daos;

import com.minshigee.authserver.daos.entities.LocalAccount;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface LocalAccountDAO extends ReactiveCrudRepository<LocalAccount, Long> {
    Mono<LocalAccount> findLocalAccountByUserEmail(String email);
}
