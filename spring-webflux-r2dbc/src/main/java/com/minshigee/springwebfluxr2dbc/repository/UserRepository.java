package com.minshigee.springwebfluxr2dbc.repository;

import com.minshigee.springwebfluxr2dbc.entity.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserRepository extends R2dbcRepository<User, Long> {
}
