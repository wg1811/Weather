package com.weatherhistoryandforecastapp.HowWasTheWeather.weather;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api")
public class WeatherController {

    private final WeatherRepository weatherRepository;

    public WeatherController(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @GetMapping("/weatherlist")    
    String listWeather() {
        return weatherRepository.listWeather().toString();
    }
    
    // Post
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    void createWeather(@RequestBody Weather weather) {
        weatherRepository.create(weather);
    }
    
    // Put
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update")
    void update(@RequestBody Weather oldWeather, @RequestBody Weather newWeather) {
        weatherRepository.update(oldWeather, newWeather);
    }

    // Delete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        weatherRepository.delete(id);
    }
}
