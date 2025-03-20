package com.weatherhistoryandforecastapp.HowWasTheWeather.users.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.FavoriteLocation;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavoriteLocationRepository extends R2dbcRepository<FavoriteLocation, Long> {
    Flux<FavoriteLocation> findByUserId(Long userId);

    Mono<FavoriteLocation> save(FavoriteLocation favorite);
}