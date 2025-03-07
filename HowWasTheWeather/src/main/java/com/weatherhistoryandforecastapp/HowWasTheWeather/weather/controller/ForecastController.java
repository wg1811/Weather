package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO.ForecastDTO;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.repository.WeatherRepository;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.service.GeocodeService;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.service.ForecastService;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.service.WeatherService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/forecast")
public class ForecastController {

    // private final WeatherRepository weatherRepository;
    private final ForecastService forecastService;
    private final GeocodeService geocodeService;

    public ForecastController(WeatherRepository weatherRepository, ForecastService forecastService, WeatherService weatherService,
            GeocodeService geocodeService) {
        // this.weatherRepository = weatherRepository;
        this.geocodeService = geocodeService;
        this.forecastService = forecastService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, world!";
    }

    // @GetMapping("/getcoordinates")
    // public Mono<ResponseEntity<Coordinates>> getCoordinates(@RequestParam String location) {
    //     return geocodeService.getCoordinates(location)
    //             .map(coordinates -> ResponseEntity.ok(coordinates))
    //             .onErrorResume(e -> {
    //                 e.printStackTrace();
    //                 if (e instanceof ResponseStatusException) {
    //                     ResponseStatusException rse = (ResponseStatusException) e;
    //                     return Mono.just(ResponseEntity.status(rse.getStatusCode()).body(null));
    //                 }
    //                 return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
    //             });
    // }

    // Getting Forecast Weather Data from Open-Meteo
    @GetMapping("/getforecast")
    public Mono<ResponseEntity<ForecastDTO>> getForecast(@RequestParam String location) {
        return geocodeService.getCoordinates(location)
                .flatMap(coordinates -> forecastService.getWeatherForecast(coordinates))
                .map(forecast -> ResponseEntity.ok(forecast))
                .onErrorResume(e -> {
                    e.printStackTrace();
                    if (e instanceof ResponseStatusException) {
                        ResponseStatusException rse = (ResponseStatusException) e;
                        return Mono.just(ResponseEntity.status(rse.getStatusCode()).body(null));
                    }
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });
    }
}

// @GetMapping("/weatherlist")
// String listWeather() {
// return weatherRepository.listWeather().toString();
// }

// // Post
// @ResponseStatus(HttpStatus.CREATED)
// @PostMapping("/")
// void createWeather(@RequestBody Weather weather) {
// weatherRepository.create(weather);
// }

// // Put
// @ResponseStatus(HttpStatus.NO_CONTENT)
// @PutMapping("/update")
// void update(@RequestBody Weather oldWeather, @RequestBody Weather newWeather)
// {
// weatherRepository.update(oldWeather, newWeather);
// }

// // Delete
// @ResponseStatus(HttpStatus.NO_CONTENT)
// @DeleteMapping("/{id}")
// void delete(@PathVariable int id) {
// weatherRepository.delete(id);
// }
// }
