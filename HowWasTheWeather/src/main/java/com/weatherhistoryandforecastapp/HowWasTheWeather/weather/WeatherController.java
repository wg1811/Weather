package com.weatherhistoryandforecastapp.HowWasTheWeather.weather;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api")
public class WeatherController {

   // private final WeatherRepository weatherRepository;
    private final WeatherService weatherService;
    private final GeocodeService geocodeService;

    public WeatherController(WeatherRepository weatherRepository, WeatherService weatherService, GeocodeService geocodeService) {
    //    this.weatherRepository = weatherRepository;
        this.weatherService = weatherService;
        this.geocodeService = geocodeService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, world!";
    }


    @GetMapping("/getcoordinates")
    public Mono<ResponseEntity<Coordinates>> getCoordinates(@RequestParam String location) { // Or do I used @RequestBody?
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
}
    // @GetMapping("/weatherlist")    
    // String listWeather() {
    //     return weatherRepository.listWeather().toString();
    // }
    
//     // Post
//     @ResponseStatus(HttpStatus.CREATED)
//     @PostMapping("/")
//     void createWeather(@RequestBody Weather weather) {
//         weatherRepository.create(weather);
//     }
    
//     // Put
//     @ResponseStatus(HttpStatus.NO_CONTENT)
//     @PutMapping("/update")
//     void update(@RequestBody Weather oldWeather, @RequestBody Weather newWeather) {
//         weatherRepository.update(oldWeather, newWeather);
//     }

//     // Delete
//     @ResponseStatus(HttpStatus.NO_CONTENT)
//     @DeleteMapping("/{id}")
//     void delete(@PathVariable int id) {
//         weatherRepository.delete(id);
//     }
// }
