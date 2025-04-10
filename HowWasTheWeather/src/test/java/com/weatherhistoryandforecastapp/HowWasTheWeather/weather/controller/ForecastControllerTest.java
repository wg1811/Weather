package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO.CurrentWeatherDTO;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO.DailyForecastDTO;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO.ForecastDTO;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO.HourlyForecastDTO;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.common.Coordinates;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.service.ForecastService;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.service.GeocodeService;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ForecastControllerTest {

    @Mock
    private ForecastService forecastService;

    @Mock
    private GeocodeService geocodeService;

    @InjectMocks
    private ForecastController forecastController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHelloEndpoint() {
        String result = forecastController.hello();
        assertEquals("Hello, world!", result);
    }

    @Test
    void testGetCurrentWeatherSuccess() {
        // Arrange
        CurrentWeatherDTO mockCurrent = new CurrentWeatherDTO(
                "2025-04-10T12:00", 15.5, 65.0, 14.8, 1,
                0.2, 0.1, 0.1, 0.0, 3, 45, 10.5, 180, 15.0);
        ForecastDTO mockForecast = new ForecastDTO(
                51.5074, -0.1278, mockCurrent, null, null);

        when(geocodeService.getCoordinates("London"))
                .thenReturn(Mono.just(new Coordinates(51.5074, -0.1278)));
        when(forecastService.getWeatherForecast(any()))
                .thenReturn(Mono.just(mockForecast));

        // Act & Assert
        Mono<ResponseEntity<CurrentWeatherDTO>> result = forecastController.getCurrentWeather("London");

        StepVerifier.create(result)
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.OK, response.getStatusCode());
                    CurrentWeatherDTO body = response.getBody();
                    return body != null &&
                            body.temperature_2m() == 15.5 &&
                            body.weather_code() == 3;
                })
                .verifyComplete();
    }

    @Test
    void testGetHourlyForecastSuccess() {
        // Arrange
        HourlyForecastDTO mockHourly = new HourlyForecastDTO(
                new String[] { "2025-04-10T12:00", "2025-04-10T13:00" },
                new double[] { 15.5, 16.0 },
                new double[] { 14.8, 15.2 },
                new int[] { 20, 25 },
                new double[] { 0.2, 0.3 },
                new double[] { 0.1, 0.2 },
                new double[] { 0.1, 0.1 },
                new double[] { 0.0, 0.0 },
                new int[] { 3, 3 },
                new int[] { 45, 50 },
                new double[] { 10.5, 11.0 },
                new int[] { 180, 185 });
        ForecastDTO mockForecast = new ForecastDTO(
                51.5074, -0.1278, null, mockHourly, null);

        when(geocodeService.getCoordinates("London"))
                .thenReturn(Mono.just(new Coordinates(51.5074, -0.1278)));
        when(forecastService.getWeatherForecast(any()))
                .thenReturn(Mono.just(mockForecast));

        // Act & Assert
        Mono<ResponseEntity<HourlyForecastDTO>> result = forecastController.getHourlyForecast("London");

        StepVerifier.create(result)
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.OK, response.getStatusCode());
                    HourlyForecastDTO body = response.getBody();
                    return body != null &&
                            body.temperature_2m()[0] == 15.5 &&
                            body.precipitation()[1] == 0.3;
                })
                .verifyComplete();
    }

    @Test
    void testGetForecastSuccess() {
        // Arrange
        CurrentWeatherDTO mockCurrent = new CurrentWeatherDTO(
                "2025-04-10T12:00", 15.5, 65.0, 14.8, 1,
                0.2, 0.1, 0.1, 0.0, 3, 45, 10.5, 180, 15.0);
        HourlyForecastDTO mockHourly = new HourlyForecastDTO(
                new String[] { "2025-04-10T12:00" }, new double[] { 15.5 },
                new double[] { 14.8 }, new int[] { 20 }, new double[] { 0.2 },
                new double[] { 0.1 }, new double[] { 0.1 }, new double[] { 0.0 },
                new int[] { 3 }, new int[] { 45 }, new double[] { 10.5 }, new int[] { 180 });
        DailyForecastDTO mockDaily = new DailyForecastDTO(
                new String[] { "2025-04-10" }, new int[] { 3 },
                new double[] { 16.5 }, new double[] { 10.5 },
                new double[] { 15.8 }, new double[] { 9.8 },
                new String[] { "2025-04-10T06:00" }, new String[] { "2025-04-10T18:00" },
                new double[] { 0.2 }, new double[] { 0.1 }, new double[] { 0.1 },
                new double[] { 0.0 }, new int[] { 20 }, new double[] { 15.0 },
                new double[] { 20.0 }, new int[] { 180 });
        ForecastDTO mockForecast = new ForecastDTO(
                51.5074, -0.1278, mockCurrent, mockHourly, mockDaily);

        when(geocodeService.getCoordinates("London"))
                .thenReturn(Mono.just(new Coordinates(51.5074, -0.1278)));
        when(forecastService.getWeatherForecast(any()))
                .thenReturn(Mono.just(mockForecast));

        // Act & Assert
        Mono<ResponseEntity<ForecastDTO>> result = forecastController.getForecast("London");

        StepVerifier.create(result)
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.OK, response.getStatusCode());
                    ForecastDTO body = response.getBody();
                    return body != null &&
                            body.latitude() == 51.5074 &&
                            body.currentDTO().temperature_2m() == 15.5 &&
                            body.dailyDTO().temperature_2m_max()[0] == 16.5;
                })
                .verifyComplete();
    }

    @Test
    void testGetCurrentWeatherError() {
        when(geocodeService.getCoordinates("InvalidLocation"))
                .thenReturn(Mono.error(new RuntimeException("Location not found")));

        Mono<ResponseEntity<CurrentWeatherDTO>> result = forecastController.getCurrentWeather("InvalidLocation");

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR &&
                        response.getBody() == null)
                .verifyComplete();
    }
}