package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.common.Coordinates;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast.ForecastData;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.historical.WeatherData;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.repository.WeatherRepository;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.service.GeocodeService;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.service.WeatherService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class WeatherController {

    // private final WeatherRepository weatherRepository;
    private final WeatherService weatherService;
    private final GeocodeService geocodeService;

    public WeatherController(WeatherRepository weatherRepository, WeatherService weatherService,
            GeocodeService geocodeService) {
        // this.weatherRepository = weatherRepository;
        this.weatherService = weatherService;
        this.geocodeService = geocodeService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, world!";
    }

    @GetMapping("/getcoordinates")
    public Mono<ResponseEntity<Coordinates>> getCoordinates(@RequestParam String location) { // Or do I used
                                                                                             // @RequestBody?
        return geocodeService.getCoordinates(location)
                .map(coordinates -> ResponseEntity.ok(coordinates))
                .onErrorResume(e -> {
                    e.printStackTrace();
                    if (e instanceof ResponseStatusException) {
                        ResponseStatusException rse = (ResponseStatusException) e;
                        return Mono.just(ResponseEntity.status(rse.getStatusCode()).body(null));
                    }
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });
    }

    // Getting Historical Weather Data from Open-Meteo
    @GetMapping("/getweather")
    public Mono<ResponseEntity<WeatherData>> getWeather(@RequestParam String location, @RequestParam String startDate,
            @RequestParam String endDate) {
        return geocodeService.getCoordinates(location)
                .flatMap(coordinates -> weatherService.getHistoricalWeather(coordinates, startDate, endDate))
                .map(weather -> ResponseEntity.ok(weather))
                .onErrorResume(e -> {
                    e.printStackTrace();
                    if (e instanceof ResponseStatusException) {
                        ResponseStatusException rse = (ResponseStatusException) e;
                        return Mono.just(ResponseEntity.status(rse.getStatusCode()).body(null));
                    }
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });
    }

    // Getting Forecast Weather Data from Open-Meteo
    @GetMapping("/getforecast")
    public Mono<ResponseEntity<ForecastData>> getForecast(@RequestParam String location) {
        return geocodeService.getCoordinates(location)
                .flatMap(coordinates -> weatherService.getWeatherForecast(coordinates))
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

    @GetMapping("/testgetweather")
    public void testGetWeather(@RequestParam String location, @RequestParam String startDate,
            @RequestParam String endDate) {
        Mono<Coordinates> coordinates = geocodeService.getCoordinates(location);
        coordinates.subscribe(coords -> weatherService.GetWeatherTest(coords, startDate, endDate));
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
