package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.common.Coordinates;

import reactor.core.publisher.Mono;

@Service
public class GeocodeService {
    private final WebClient webClient;
    @Value("${google.maps.api.key}")
    private String apiKey;

    public GeocodeService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://maps.googleapis.com/maps/api/geocode/json").build();
    }

    public Mono<Coordinates> getCoordinates(String address) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("address", address)
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> {
                            if (response.statusCode().equals(HttpStatusCode.valueOf(404))) {
                                return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "No results found for the given address."));
                            } else {
                                return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                        "Failed to get coordinates."));
                            }
                        })
                .bodyToMono(JsonNode.class)
                .map(JsonNode -> {
                    try {
                        JsonNode results = JsonNode.get("results");
                        if (results != null && results.isArray() && results.size() > 0) {
                            JsonNode geometry = results.get(0).get("geometry");
                            if (geometry != null) {
                                JsonNode location = geometry.get("location");
                                if (location != null) {
                                    double lat = location.get("lat").asDouble();
                                    double lng = location.get("lng").asDouble();
                                    return new Coordinates(lat, lng);
                                }
                            }
                        }
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                                "Failed to extract coordinates.");
                    } catch (Exception e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to parse response.",
                                e);
                    }
                });
    }
}

// In case I want to use this elsewhere (was from a Controller):
// @GetMapping("/getcoordinates")
// public Mono<ResponseEntity<Coordinates>> getCoordinates(@RequestParam String
// location) {
// return geocodeService.getCoordinates(location)
// .map(coordinates -> ResponseEntity.ok(coordinates))
// .onErrorResume(e -> {
// e.printStackTrace();
// if (e instanceof ResponseStatusException) {
// ResponseStatusException rse = (ResponseStatusException) e;
// return Mono.just(ResponseEntity.status(rse.getStatusCode()).body(null));
// }
// return
// Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
// });
// }