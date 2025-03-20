package com.weatherhistoryandforecastapp.HowWasTheWeather.users.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.User;

import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends R2dbcRepository<User, Long> { // might want to be this:
                                                                      // ReactiveCrudRepository<User, Long> for
                                                                      // different DBs (e.g. MongoDB).
    Mono<User> findByEmail(String email);

    Mono<Boolean> existsByEmail(String email);

}
