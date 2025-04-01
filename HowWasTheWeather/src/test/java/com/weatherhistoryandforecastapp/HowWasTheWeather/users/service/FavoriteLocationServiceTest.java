package com.weatherhistoryandforecastapp.HowWasTheWeather.users.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.FavoriteLocation;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.repository.FavoriteLocationRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FavoriteLocationServiceTest {
    private FavoriteLocationService service;
    private FavoriteLocationRepository repository; 

@BeforeEach
void setUp() {
    repository = mock(FavoriteLocationRepository.class);
    service = new FavoriteLocationService(repository);
};

    @Test
    void addFavorite_savesAndReturnsFavorite() {
// Arrange
        Long userId = 1L;
        String name = "London";
        BigDecimal latitude = new BigDecimal("51.5074");
        BigDecimal longitude = new BigDecimal("-0.1278");
        FavoriteLocation favorite = new FavoriteLocation();
        favorite.setUserId(userId);
        favorite.setName(name);
        favorite.setLatitude(latitude);
        favorite.setLongitude(longitude);
        favorite.setCreatedAt(LocalDateTime.now()); // Will be set by service
        when(repository.save(any(FavoriteLocation.class))).thenReturn(Mono.just(favorite));

        // Act
        Mono<FavoriteLocation> result = service.addFavorite(userId, name, latitude, longitude);

        // Assert
        StepVerifier.create(result)
            .assertNext(saved -> {
                assertThat(saved.getUserId()).isEqualTo(userId);
                assertThat(saved.getName()).isEqualTo(name);
                assertThat(saved.getLatitude()).isEqualTo(latitude);
                assertThat(saved.getLongitude()).isEqualTo(longitude);
                assertThat(saved.getCreatedAt()).isNotNull();
            })
            .verifyComplete();

        verify(repository).save(argThat(fav -> 
            fav.getUserId().equals(userId) && 
            fav.getName().equals(name) && 
            fav.getLatitude().equals(latitude) && 
            fav.getLongitude().equals(longitude)));
    }

@Test
    void getFavoritesByUser_returnsUserFavorites() {
        // Arrange
        Long userId = 1L;
        FavoriteLocation fav1 = new FavoriteLocation();
        fav1.setUserId(userId);
        fav1.setName("London");
        fav1.setLatitude(new BigDecimal("51.5074"));
        fav1.setLongitude(new BigDecimal("-0.1278"));
        FavoriteLocation fav2 = new FavoriteLocation();
        fav2.setUserId(userId);
        fav2.setName("Paris");
        fav2.setLatitude(new BigDecimal("48.8566"));
        fav2.setLongitude(new BigDecimal("2.3522"));
        when(repository.findByUserId(userId)).thenReturn(Flux.just(fav1, fav2));

        // Act
        Flux<FavoriteLocation> result = service.getFavoritesByUser(userId);

        // Assert
        StepVerifier.create(result)
            .assertNext(fav -> {
                assertThat(fav.getUserId()).isEqualTo(userId);
                assertThat(fav.getName()).isEqualTo("London");
            })
            .assertNext(fav -> {
                assertThat(fav.getUserId()).isEqualTo(userId);
                assertThat(fav.getName()).isEqualTo("Paris");
            })
            .verifyComplete();

        verify(repository).findByUserId(userId);
    }

    @Test
    void deleteFavorite_deletesById() {
        // Arrange
        Long id = 1L;
        when(repository.deleteById(id)).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = service.deleteFavorite(id);

        // Assert
        StepVerifier.create(result)
            .verifyComplete();

        verify(repository).deleteById(id);
    }
}
