package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.common.Coordinates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
                .expectNextMatches(coords -> 
                    coords.lat() == 37.7749 && coords.lng() == -122.4194)
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
                .expectErrorMatches(throwable -> 
                    throwable instanceof ResponseStatusException &&
                    ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR &&
                    throwable.getMessage().contains("Failed to get coordinates"))
                .verify();
    }
}

// package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.service;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.http.HttpStatus;
// import org.springframework.web.client.HttpClientErrorException;
// import org.springframework.web.reactive.function.client.ClientResponse;
// import org.springframework.web.reactive.function.client.WebClient;
// import org.springframework.web.server.ResponseStatusException;
// import org.junit.jupiter.api.extension.ExtendWith;


// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.databind.node.ArrayNode;
// import com.fasterxml.jackson.databind.node.ObjectNode;
// import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.common.Coordinates;

// import reactor.core.publisher.Mono;
// import reactor.test.StepVerifier;

// @ExtendWith(MockitoExtension.class)
// public class GeocodeServiceTest {

// @Mock
//     private WebClient webClient;
//     @Mock
//     private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;
//     @Mock
//     private WebClient.RequestHeadersSpec<?> requestHeadersSpec;
//     @Mock
//     private WebClient.ResponseSpec responseSpec;
//     @InjectMocks
//     private GeocodeService service;



//     @BeforeEach
//     @SuppressWarnings({ "unchecked", "rawtypes" })
//     void setUp() {
// // Mock Webclient and chain
//         webClient = Mockito.mock(WebClient.class);
//         requestHeadersUriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
//         requestHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
//         responseSpec = Mockito.mock(WebClient.ResponseSpec.class);
//  //       clientResponse = Mockito.mock(ClientResponse.class);
        

//         // Stub the chain
//         when((WebClient.RequestHeadersUriSpec) webClient.get()).thenReturn((WebClient.RequestHeadersUriSpec) requestHeadersUriSpec);
//         when(requestHeadersUriSpec.uri(any(java.util.function.Function.class))).thenReturn(requestHeadersSpec);
//         when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
//  //       when(clientResponse.statusCode()).thenReturn(HttpStatus.NOT_FOUND);


//         // Create the service
//         WebClient.Builder builder = Mockito.mock(WebClient.Builder.class);
//         when(builder.baseUrl("https://maps.googleapis.com/maps/api/geocode/json")).thenReturn(builder);
//         when(builder.build()).thenReturn(webClient);

//         service = new GeocodeService(builder);
// }

// @Test
//     void getCoordinates_returnsCoordinatesForValidAddress() {
//         // Arrange
//         String address = "1600 Amphitheatre Parkway, Mountain View, CA";
//         ObjectMapper mapper = new ObjectMapper();
//         ObjectNode location = mapper.createObjectNode()
//             .put("lat", 37.4221)
//             .put("lng", -122.0841);
//         ObjectNode geometry = mapper.createObjectNode().set("location", location);
//         ArrayNode results = mapper.createArrayNode().add(mapper.createObjectNode().set("geometry", geometry));
//         JsonNode mockResponse = mapper.createObjectNode().set("results", results);
//         when(responseSpec.bodyToMono(JsonNode.class)).thenReturn(Mono.just(mockResponse));

//         // Act
//         Mono<Coordinates> result = service.getCoordinates(address);

//         // Assert
//         StepVerifier.create(result)
//             .assertNext(coords -> {
//                 assertThat(coords.lat()).isEqualTo(37.4221);
//                 assertThat(coords.lng()).isEqualTo(-122.0841);
//             })
//             .verifyComplete();
//     }

//     @Test
//     void getCoordinates_throwsNotFoundFor404() {
//    ClientResponse clientResponse = ClientResponse.create(HttpStatus.NOT_FOUND)
//             .build();
    
//     when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
//     when(responseSpec.bodyToMono(any(Class.class)))
//             .thenReturn(Mono.error(HttpClientErrorException.create(
//                     HttpStatus.NOT_FOUND,
//                     "Not Found",
//                     null,
//                     null,
//                     null)));


//             // Act
//         Mono<Coordinates> result = service.getCoordinates("the wrong address not anything");

//         // Assert
//         StepVerifier.create(result)
//             .expectErrorMatches(throwable ->
//                 throwable instanceof ResponseStatusException &&
//                 ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.NOT_FOUND &&
//                 "No results found for the given address.".equals(throwable.getMessage()))
//             .verify();
//     }

//     @Test
//     void getCoordinates_throwsInternalErrorForEmptyResults() {
//         String address = "Empty Response Place";
//         ObjectMapper mapper = new ObjectMapper();
//         JsonNode mockResponse = mapper.createObjectNode().set("results", mapper.createArrayNode()); // Empty results
//         when(responseSpec.bodyToMono(JsonNode.class)).thenReturn(Mono.just(mockResponse));

//         Mono<Coordinates> result = service.getCoordinates(address);

//         // Assuming actual message from GeocodeService
//         StepVerifier.create(result)
//             .expectErrorMatches(throwable ->
//                 throwable instanceof ResponseStatusException &&
//                 ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR &&
//                 "Failed to get coordinates.".equals(throwable.getMessage()))
//             .verify();
//     }

//     @Test
//     void getCoordinates_throwsInternalErrorForInvalidJson() {
//         String address = "Bad JSON Place";
//         when(responseSpec.bodyToMono(JsonNode.class))
//         .thenReturn(Mono.just(null)); // This will cause a NullPointerException in the map function, which shows as an error, so change this.
        
//         Mono<Coordinates> result = service.getCoordinates(address);

//         // Assuming actual message from GeocodeService
//         StepVerifier.create(result)
//             .expectErrorMatches(throwable ->
//                 throwable instanceof ResponseStatusException &&
//                 ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR &&
//                 "Failed to get coordinates.".equals(throwable.getMessage()))
//             .verify();
//     }
// }
