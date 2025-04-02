package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast;

public record CurrentWeatherUnits(
        String time,
        String interval,
        String temperature_2m,
        String relative_humidity_2m,
        String apparent_temperature,
        String is_day,
        String precipitation,
        String rain,
        String showers,
        String snowfall,
        String weather_code,
        String cloud_cover,
        String wind_speed_10m,
        String wind_direction_10m,
        String wind_gusts_10m) {
}