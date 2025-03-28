package com.weatherhistoryandforecastapp.HowWasTheWeather.users.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;  //  Should I be using this for the responses?
import org.springframework.stereotype.Service;

import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.FavoriteLocation;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.repository.FavoriteLocationRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FavoriteLocationService {
    private final FavoriteLocationRepository repository;

    // @Autowired  // Not sure if this is necessary
    public FavoriteLocationService(FavoriteLocationRepository repository) {
        this.repository = repository;
    }

    public Mono<FavoriteLocation> addFavorite(Long userId, String name, BigDecimal latitude, BigDecimal longitude) {
        FavoriteLocation favorite = new FavoriteLocation();
        favorite.setUserId(userId);
        favorite.setName(name);
        favorite.setLatitude(latitude);
        favorite.setLongitude(longitude);
        favorite.setCreatedAt(LocalDateTime.now());
        System.out.println("FavoriteLocationService.addFavorite: favorite = " + favorite.getName()); // Debugging
        return repository.save(favorite);
    }

    public Flux<FavoriteLocation> getFavoritesByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    public Mono<Void> deleteFavorite(Long id) {
        return repository.deleteById(id);
    }
}
