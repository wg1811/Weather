package com.weatherhistoryandforecastapp.HowWasTheWeather.weather;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Service
public class GeocodeService {
    private final WebClient webClient;
    private final String apiKey = "AIzaSyA-8Z5fBmCfdh7SHivvAhd2KKMKxD_V00o"; // Need to get this from application properties or environment variables

    public GeocodeService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://maps.googleapis.com/maps/api/geocode/json").build(); // Not sure this is the right URL.
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
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to extract coordinates.");
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to parse response.", e);
            }
        });    }
}
   