package com.weatherhistoryandforecastapp.HowWasTheWeather.users.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.FavoriteLocation;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.FavoriteLocationRequest;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.User;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.service.FavoriteLocationService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FavoriteLocationControllerTest {

    @Mock
    private FavoriteLocationService favoriteService;

    @InjectMocks
    private FavoriteLocationController controller;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User("test@example.com", "password");
        mockUser.setId(1L);
        mockUser.setRoles(new String[] { "USER" });
    }

    @Test
    void testAddFavoriteSuccess() {
        // Arrange
        FavoriteLocationRequest request = new FavoriteLocationRequest("London",
                BigDecimal.valueOf(51.5074), BigDecimal.valueOf(-0.1278));
        FavoriteLocation mockFavorite = new FavoriteLocation(1L, "London",
                BigDecimal.valueOf(51.5074), BigDecimal.valueOf(-0.1278));
        mockFavorite.setId(1L);
        mockFavorite.setCreatedAt(LocalDateTime.now());

        when(favoriteService.addFavorite(eq(1L), eq("London"),
                eq(request.getLatitude()), eq(request.getLongitude())))
                .thenReturn(Mono.just(mockFavorite));

        // Act
        Mono<ResponseEntity<FavoriteLocation>> result = controller.addFavorite(request, Mono.just(mockUser));

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.OK &&
                        response.getBody().getName().equals("London") &&
                        response.getBody().getLatitude().compareTo(BigDecimal.valueOf(51.5074)) == 0 &&
                        response.getBody().getUserId().equals(1L))
                .verifyComplete();
    }

    @Test
    void testAddFavoriteUnauthorized() {
        // Arrange
        FavoriteLocationRequest request = new FavoriteLocationRequest("London",
                BigDecimal.valueOf(51.5074), BigDecimal.valueOf(-0.1278));

        // Act
        Mono<ResponseEntity<FavoriteLocation>> result = controller.addFavorite(request, Mono.empty());

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.UNAUTHORIZED &&
                        response.getBody() == null)
                .verifyComplete();
    }

    @Test
    void testGetFavorites() {
        // Arrange
        FavoriteLocation mockFavorite = new FavoriteLocation(1L, "London",
                BigDecimal.valueOf(51.5074), BigDecimal.valueOf(-0.1278));
        mockFavorite.setId(1L);
        mockFavorite.setCreatedAt(LocalDateTime.now());

        when(favoriteService.getFavoritesByUser(1L))
                .thenReturn(Flux.just(mockFavorite));

        // Act
        Flux<FavoriteLocation> result = controller.getFavorites(Mono.just(mockUser));

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(favorite -> favorite.getName().equals("London") &&
                        favorite.getLongitude().compareTo(BigDecimal.valueOf(-0.1278)) == 0 &&
                        favorite.getUserId().equals(1L))
                .verifyComplete();
    }

    @Test
    void testDeleteFavoriteSuccess() {
        // Arrange
        when(favoriteService.deleteFavorite(1L))
                .thenReturn(Mono.empty());

        // Act
        Mono<ResponseEntity<Void>> result = controller.deleteFavorite(1L);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.NO_CONTENT)
                .verifyComplete();
    }

    @Test
    void testDeleteFavoriteNotFound() {
        // Arrange
        when(favoriteService.deleteFavorite(999L))
                .thenReturn(Mono.error(new RuntimeException("Favorite not found")));

        // Act
        Mono<ResponseEntity<Void>> result = controller.deleteFavorite(999L);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.NOT_FOUND)
                .verifyComplete();
    }
}