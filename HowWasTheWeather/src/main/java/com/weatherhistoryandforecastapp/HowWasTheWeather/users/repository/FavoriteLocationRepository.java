package com.weatherhistoryandforecastapp.HowWasTheWeather.users.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.FavoriteLocation;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavoriteLocationRepository extends R2dbcRepository<FavoriteLocation, Long> {
    // Not sure I need the query stuff? 
    // @Query("SELECT * FROM favorite_locations WHERE user_id = :userId")
    Flux<FavoriteLocation> findByUserId(Long userId);

    // Checks if user already has a favorite location with the same name
    // @Query("SELECT * FROM favorite_locations WHERE user_id = :userId AND location_name = :locationName LIMIT 1")
    Mono<FavoriteLocation> save(FavoriteLocation favorite);
}