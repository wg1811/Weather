package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.common.Coordinates;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class) // Enables Mockito for JUnit 5
public class GeocodeServiceTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private GeocodeService geocodeService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        // Mock the WebClient chain
        when(webClientBuilder.baseUrl(any())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        geocodeService = new GeocodeService(webClientBuilder);
    }

    @Test
    public void testGetCoordinates_Success() throws Exception {
        // Sample JSON response from Google Maps API
        String jsonResponse = "{ \"results\": [ { \"geometry\": { \"location\": { \"lat\": 37.7749, \"lng\": -122.4194 } } } ], \"status\": \"OK\" }";
        JsonNode responseNode = objectMapper.readTree(jsonResponse);

        // Mock the WebClient response
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec); // No errors
        when(responseSpec.bodyToMono(JsonNode.class)).thenReturn(Mono.just(responseNode));

        // Test the service
        Mono<Coordinates> coordinatesMono = geocodeService.getCoordinates("San Francisco, CA");

        // Verify the result using StepVerifier
        StepVerifier.create(coordinatesMono)
                .expectNextMatches(coords -> coords.lat() == 37.7749 && coords.lng() == -122.4194)
                .verifyComplete();
    }

    @Test
    public void testGetCoordinates_NoResults() throws Exception {
        // Sample JSON response with no results
        String jsonResponse = "{ \"results\": [], \"status\": \"ZERO_RESULTS\" }";
        JsonNode responseNode = objectMapper.readTree(jsonResponse);

        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec); // No errors
        when(responseSpec.bodyToMono(JsonNode.class)).thenReturn(Mono.just(responseNode));

        // Test the service
        Mono<Coordinates> coordinatesMono = geocodeService.getCoordinates("Invalid Address");

        // Verify the exception using StepVerifier
        StepVerifier.create(coordinatesMono)
                .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException &&
                        ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR &&
                        throwable.getMessage().contains("Failed to get coordinates"))
                .verify();
    }
}