package com.weatherhistoryandforecastapp.HowWasTheWeather.weather;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class WeatherRepository {
    //I'm not sure how best to store weather data.  I'm going to try a list for now.
    private List<Weather> weatherList = new ArrayList<>();

    List<Weather> listWeather() {
        return weatherList;
    }

    // I think this will be called from the WeatherServices class.
    Weather create(Weather weather) {
        weatherList.add(weather);
        return weather;
    }


    // This isn't going to work like this.  You need to find the weather object in the list and update it.
    Weather update(Weather oldWeather, Weather newWeather) {
        weatherList.remove(oldWeather);
        weatherList.add(newWeather);
        return newWeather;
    }

    // Delete
    void delete(int id) {
        weatherList.removeIf(weather -> weather.id() == id);
    }

    @PostConstruct
    private void init() {
        weatherList.add(new Weather(1, 40.7128, 74.0060, LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        weatherList.add(new Weather(2, 12.7128, 74.0060, LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        }
}