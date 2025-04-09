package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO.CurrentWeatherDTO;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO.ForecastDTO;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.common.Coordinates;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast.CurrentWeatherData;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast.CurrentWeatherUnits;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast.DailyForecastData;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast.DailyForecastUnits;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast.ForecastData;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast.HourlyForecastData;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast.HourlyForecastUnits;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class ForecastServiceTest {

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

    private ForecastService forecastService;

    @BeforeEach
    void setUp() {
        // Configure mocks before instantiating the service
        when(webClientBuilder.baseUrl("https://api.open-meteo.com/v1/forecast")).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);

        // Instantiate the service after mocks are set up
        forecastService = new ForecastService(webClientBuilder);
    }

    @Test
    void getWeatherForecast_successfulResponse_returnsForecastDTO() {
        // Arrange
        Coordinates coordinates = new Coordinates(40.7128, -74.0060); // Example: New York City

        // Mock WebClient response
        ForecastData mockForecastData = createMockForecastData();
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(java.util.function.Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ForecastData.class)).thenReturn(Mono.just(mockForecastData));

        // Act
        Mono<ForecastDTO> result = forecastService.getWeatherForecast(coordinates);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(forecastDTO -> {
                    CurrentWeatherDTO current = forecastDTO.getCurrentWeather();
                    return forecastDTO.latitude() == 40.7128 &&
                            forecastDTO.longitude() == -74.0060 &&
                            current.temperature_2m() == 20 &&
                            current.weather_code() == 0;
                })
                .verifyComplete();
    }

    @Test
    void getWeatherForecast_apiReturns404_throwsNotFoundException() {
        // Arrange
        Coordinates coordinates = new Coordinates(40.7128, -74.0060);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(java.util.function.Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ForecastData.class))
                .thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No weather data found")));

        // Act
        Mono<ForecastDTO> result = forecastService.getWeatherForecast(coordinates);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException &&
                        ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.NOT_FOUND &&
                        throwable.getMessage().contains("No weather data found"))
                .verify();
    }

    private ForecastData createMockForecastData() {
        String now = "2025-04-02T12:00Z"; // Fixed time for consistency
        CurrentWeatherData current = new CurrentWeatherData(
                now, 300.0, 20.0, 60, 22.0, 1, 0.0, 0.0, 0.0, 0.0, 0, 50, 10.0, 180, 15.0);
        HourlyForecastData hourly = new HourlyForecastData(
                new String[] { now, "2025-04-02T13:00Z" },
                new double[] { 20.0, 21.0 }, new double[] { 22.0, 23.0 }, new int[] { 10, 20 },
                new double[] { 0.0, 0.1 },
                new double[] { 0.0, 0.0 }, new double[] { 0.0, 0.0 }, new double[] { 0.0, 0.0 },
                new double[] { 0.0, 0.0 },
                new int[] { 0, 1 }, new double[] { 1013.0, 1012.0 }, new double[] { 1010.0, 1009.0 },
                new int[] { 50, 60 },
                new double[] { 24000.0, 23000.0 }, new double[] { 10.0, 12.0 }, new int[] { 180,
                        190 },
                new double[] { 5.0, 5.5 },
                new int[] { 1, 1 }, new int[] { 3600, 3600 });
        DailyForecastData daily = new DailyForecastData(
                new String[] { now }, new int[] { 0 }, new double[] { 25.0 }, new double[] { 15.0 },
                new double[] { 27.0 }, new double[] { 17.0 },
                new String[] { "2025-04-02T06:00Z" }, new String[] { "2025-04-02T18:00Z" }, new double[] { 43200.0 },
                new double[] { 36000.0 }, new double[] { 6.0 }, new double[] { 7.0 }, new double[] { 0.0 },
                new double[] { 0.0 },
                new double[] { 0.0 }, new double[] { 0.0 }, new double[] { 0.0 }, new int[] { 10 },
                new double[] { 15.0 },
                new double[] { 20.0 }, new int[] { 180 });

        // Minimal units - not used in DTO mapping but required for ForecastData
        CurrentWeatherUnits currentUnits = new CurrentWeatherUnits("iso8601",
                "seconds", "°C", "%", "°C", "", "mm", "mm", "mm", "cm", "WMO code", "%",
                "km/h", "degrees", "km/h");
        HourlyForecastUnits hourlyUnits = new HourlyForecastUnits("iso8601", "°C",
                "°C", "%", "mm", "mm", "mm", "cm", "cm", "WMO code", "hPa", "hPa", "%", "m",
                "km/h", "degrees", "", "", "seconds");
        DailyForecastUnits dailyUnits = new DailyForecastUnits("iso8601", "WMO code",
                "°C", "°C", "°C", "°C", "iso8601", "iso8601", "seconds", "seconds", "", "",
                "mm", "mm", "mm", "cm", "hours", "%", "km/h", "km/h", "degrees");

        return new ForecastData(
                1, 40.7128, -74.0060, "auto", 100.0, 0.0, 123.45, now,
                current, currentUnits, dailyUnits, hourlyUnits, daily, hourly);
    }
}

// package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.service;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;

// import java.util.function.Function;
// import java.util.function.Predicate;

// import static org.assertj.core.api.Assertions.assertThat;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpStatusCode;
// import org.springframework.web.reactive.function.client.ClientResponse;
// import org.springframework.web.reactive.function.client.WebClient;
// import org.springframework.web.server.ResponseStatusException;

// import
// com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO.CurrentWeatherDTO;
// import
// com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO.DailyForecastDTO;
// import
// com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO.ForecastDTO;
// import
// com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO.HourlyForecastDTO;
// import
// com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.common.Coordinates;
// import
// com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast.CurrentWeatherData;
// import
// com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast.CurrentWeatherUnits;
// import
// com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast.DailyForecastData;
// import
// com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast.DailyForecastUnits;
// import
// com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast.ForecastData;
// import
// com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast.HourlyForecastData;
// import
// com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast.HourlyForecastUnits;

// import reactor.core.publisher.Mono;
// import reactor.test.StepVerifier;

// public class ForecastServiceTest {

// private ForecastService service;
// private WebClient webClient;
// private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;
// private WebClient.RequestHeadersSpec<?> requestHeadersSpec;
// private WebClient.ResponseSpec responseSpec;

// @BeforeEach
// @SuppressWarnings({ "unchecked", "rawtypes" })
// void setUp() {

// // Mock WebClient and chain
// webClient = Mockito.mock(WebClient.class);
// requestHeadersUriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
// requestHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
// responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

// // Stub the chain
// when((WebClient.RequestHeadersUriSpec)
// webClient.get()).thenReturn((WebClient.RequestHeadersUriSpec)
// requestHeadersUriSpec);
// when(requestHeadersUriSpec.uri(any(java.util.function.Function.class))).thenReturn(requestHeadersSpec);
// when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
// when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);

// // Create the service
// WebClient.Builder builder = Mockito.mock(WebClient.Builder.class);
// when(builder.baseUrl("https://api.open-meteo.com/v1/forecast")).thenReturn(builder);
// when(builder.build()).thenReturn(webClient);

// service = new ForecastService(builder);
// }

// @Test
// void getWeatherForecast_returnsForecastDTO() {
// // Arrange
// Coordinates coords = new Coordinates(37.4221, -122.0841);
// ForecastData mockData = createMockForecastData();
// when(responseSpec.bodyToMono(ForecastData.class)).thenReturn(Mono.just(mockData));

// // Act
// Mono<ForecastDTO> result = service.getWeatherForecast(coords);

// // Assert
// StepVerifier.create(result)
// .assertNext(dto -> {
// assertThat(dto.latitude()).isEqualTo(37.4221);
// assertThat(dto.longitude()).isEqualTo(-122.0841);
// CurrentWeatherDTO current = dto.getCurrentWeather();
// assertThat(current.temperature_2m()).isEqualTo(20.0);
// assertThat(current.weather_code()).isEqualTo(0);
// assertThat(current.time()).isEqualTo("2025-04-02T12:00Z");
// HourlyForecastDTO hourly = dto.getHourlyForecast();
// assertThat(hourly.temperature_2m()).containsExactly(20.0, 21.0);
// assertThat(hourly.precipitation()).containsExactly(0.0, 0.1);
// DailyForecastDTO daily = dto.getDailyForecast();
// assertThat(daily.temperature_2m_max()).containsExactly(25.0);
// assertThat(daily.sunrise()).containsExactly("2025-04-02T06:00Z");
// assertThat(daily.precipitation_sum()).containsExactly(0.0);
// })
// .verifyComplete();
// }

// @Test
// void getWeatherForecast_throwsNotFoundFor404() {
// // Arrange
// Coordinates coords = new Coordinates(0, 0);
// Predicate<HttpStatusCode> isError = status -> status.is4xxClientError() ||
// status.is5xxServerError();
// Function<ClientResponse, Mono<? extends Throwable>> errorHandler = response
// ->
// Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No weather data
// found for the given coordinates."));
// when(responseSpec.onStatus(isError, errorHandler))
// .thenReturn(responseSpec);
// // Act
// Mono<ForecastDTO> result = service.getWeatherForecast(coords);

// // Assert
// StepVerifier.create(result)
// .expectErrorMatches(throwable ->
// throwable instanceof ResponseStatusException &&
// ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.NOT_FOUND
// &&
// "No weather data found for the given
// coordinates.".equals(throwable.getMessage()))
// .verify();
// }

// private ForecastData createMockForecastData() {
// String now = "2025-04-02T12:00Z"; // Fixed time for consistency
// CurrentWeatherData current = new CurrentWeatherData(
// now, 300.0, 20.0, 60, 22.0, 1, 0.0, 0.0, 0.0, 0.0, 0, 50, 10.0, 180, 15.0);
// HourlyForecastData hourly = new HourlyForecastData(
// new String[]{now, "2025-04-02T13:00Z"},
// new double[]{20.0, 21.0}, new double[]{22.0, 23.0}, new int[]{10, 20}, new
// double[]{0.0, 0.1},
// new double[]{0.0, 0.0}, new double[]{0.0, 0.0}, new double[]{0.0, 0.0}, new
// double[]{0.0, 0.0},
// new int[]{0, 1}, new double[]{1013.0, 1012.0}, new double[]{1010.0, 1009.0},
// new int[]{50, 60},
// new double[]{24000.0, 23000.0}, new double[]{10.0, 12.0}, new int[]{180,
// 190}, new double[]{5.0, 5.5},
// new int[]{1, 1}, new int[]{3600, 3600});
// DailyForecastData daily = new DailyForecastData(
// new String[]{now}, new int[]{0}, new double[]{25.0}, new double[]{15.0}, new
// double[]{27.0}, new double[]{17.0},
// new String[]{"2025-04-02T06:00Z"}, new String[]{"2025-04-02T18:00Z"}, new
// double[]{43200.0},
// new double[]{36000.0}, new double[]{6.0}, new double[]{7.0}, new
// double[]{0.0}, new double[]{0.0},
// new double[]{0.0}, new double[]{0.0}, new double[]{0.0}, new int[]{10}, new
// double[]{15.0},
// new double[]{20.0}, new int[]{180});

// // Minimal units - not used in DTO mapping but required for ForecastData
// CurrentWeatherUnits currentUnits = new CurrentWeatherUnits("iso8601",
// "seconds", "°C", "%", "°C", "", "mm", "mm", "mm", "cm", "WMO code", "%",
// "km/h", "degrees", "km/h");
// HourlyForecastUnits hourlyUnits = new HourlyForecastUnits("iso8601", "°C",
// "°C", "%", "mm", "mm", "mm", "cm", "cm", "WMO code", "hPa", "hPa", "%", "m",
// "km/h", "degrees", "", "", "seconds");
// DailyForecastUnits dailyUnits = new DailyForecastUnits("iso8601", "WMO code",
// "°C", "°C", "°C", "°C", "iso8601", "iso8601", "seconds", "seconds", "", "",
// "mm", "mm", "mm", "cm", "hours", "%", "km/h", "km/h", "degrees");

// return new ForecastData(
// 1, 37.4221, -122.0841, "auto", 100.0, 0.0, 123.45, now,
// current, currentUnits, dailyUnits, hourlyUnits, daily, hourly);
// }

// }
