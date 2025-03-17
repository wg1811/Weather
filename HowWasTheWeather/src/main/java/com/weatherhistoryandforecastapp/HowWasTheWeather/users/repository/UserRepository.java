package com.weatherhistoryandforecastapp.HowWasTheWeather.users.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.User;

import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> { //ReactiveCrudRepository<User, Long> might need to be this: extends R2dbcRepository<User, Long>
    Mono<User> findByEmail(String email);

    Mono<Boolean> existsByEmail(String email);

}
