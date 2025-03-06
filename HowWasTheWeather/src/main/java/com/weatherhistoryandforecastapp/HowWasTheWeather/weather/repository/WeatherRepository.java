package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.historical.WeatherData;

//import jakarta.annotation.PostConstruct;  // This was to populate the list with some initial data.  (and is the wrong import? should be using a spring version?)

@Repository
public class WeatherRepository {

    private List<WeatherData> weatherList = new ArrayList<>();
    //I'm not sure how best to store weather data.  I'm going to try a list for now.
   

    // I think this will be called from the WeatherServices class.
    WeatherData create(WeatherData weather) {
        weatherList.add(weather);
        return weather;
    }


    // // This isn't going to work like this.  You need to find the weather object in the list and update it.
    // Weather update(Weather oldWeather, Weather newWeather) {
    //     weatherList.remove(oldWeather);
    //     weatherList.add(newWeather);
    //     return newWeather;
    // }

    // // Delete
    // void delete(int id) {
    //     weatherList.removeIf(weather -> weather.id() == id);
    // }

    // @PostConstruct
    // private void init() {
    //     weatherList.add(new Weather(1, 40.7128, 74.0060, LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
    //     weatherList.add(new Weather(2, 12.7128, 74.0060, LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
    //     }
}