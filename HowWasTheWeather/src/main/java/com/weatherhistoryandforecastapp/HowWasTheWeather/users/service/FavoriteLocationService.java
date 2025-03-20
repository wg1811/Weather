package com.weatherhistoryandforecastapp.HowWasTheWeather.users.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.FavoriteLocation;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.repository.FavoriteLocationRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FavoriteLocationService {
    private final FavoriteLocationRepository repository;

    @Autowired
    public FavoriteLocationService(FavoriteLocationRepository repository) {
        this.repository = repository;
    }

    public Mono<FavoriteLocation> saveFavorite(Long userId, String locationName) {
        FavoriteLocation favorite = new FavoriteLocation();
        favorite.setUserId(userId);
        favorite.setLocationName(locationName);
        favorite.setCreatedAt(LocalDateTime.now());
        return repository.save(favorite);
    }

    public Flux<FavoriteLocation> getFavoritesByUser(Long userId) {
        return repository.findByUserId(userId);
    }
}
