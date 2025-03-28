package com.weatherhistoryandforecastapp.HowWasTheWeather.users.controller;

import org.springframework.http.HttpStatus;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.FavoriteLocation;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.FavoriteLocationRequest;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.User;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.service.FavoriteLocationService;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteLocationController {
    private final FavoriteLocationService favoriteService;

//     @Autowired // Unsure if this is necessary
    public FavoriteLocationController(FavoriteLocationService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public Mono<ResponseEntity<FavoriteLocation>> addFavorite(
            @Valid @RequestBody FavoriteLocationRequest request,
            @AuthenticationPrincipal Mono<User> userMono) {
        return userMono.flatMap(user -> favoriteService.addFavorite(user.getId(), request.getName(), request.getLatitude(), request.getLongitude())
                .map(favorite -> ResponseEntity.ok(favorite)));
    }

    @GetMapping
    public Flux<FavoriteLocation> getFavorites(@AuthenticationPrincipal Mono<User> userMono) {
        return userMono.doOnNext(user -> System.out.println("User: " + user))
                .flatMapMany(user -> favoriteService.getFavoritesByUser(user.getId()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteFavorite(@PathVariable Long id) {
        return favoriteService.deleteFavorite(id)
            .then(Mono.fromSupplier(() -> ResponseEntity.noContent().<Void>build()))
            .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).<Void>build()));
    }
}