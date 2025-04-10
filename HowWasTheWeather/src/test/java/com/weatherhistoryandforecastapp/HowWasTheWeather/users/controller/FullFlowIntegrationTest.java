package com.weatherhistoryandforecastapp.HowWasTheWeather.users.controller;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.weatherhistoryandforecastapp.HowWasTheWeather.users.model.FavoriteLocationRequest;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.model.AuthRequest;
import com.weatherhistoryandforecastapp.HowWasTheWeather.users.security.model.AuthResponse;

@SpringBootTest
@AutoConfigureWebTestClient
public class FullFlowIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testSignupLoginAndAddFavorite() {
        // Signup
        AuthRequest signupRequest = new AuthRequest("testuser@example.com", "password123");
        String token = webTestClient.post()
                .uri("/api/signup")
                .bodyValue(signupRequest)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(AuthResponse.class)
                .getResponseBody().blockFirst().getToken();

        // Add Favorite
        FavoriteLocationRequest favoriteRequest = new FavoriteLocationRequest("London",
                BigDecimal.valueOf(51.5074), BigDecimal.valueOf(-0.1278));

        webTestClient.post()
                .uri("/api/favorites")
                .header("Authorization", "Bearer " + token)
                .bodyValue(favoriteRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("London");
    }
}