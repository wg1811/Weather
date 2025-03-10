package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast;

public record CurrentWeatherData(
        String time,
        double interval,
        double temperature_2m,
        double relative_humidity_2m,
        double apparent_temperature,
        int is_day,
        double precipitation,
        double rain,
        double showers,
        double snowfall,
        int weather_code,
        int cloud_cover,
        double wind_speed_10m,
        int wind_direction_10m,
        double wind_gusts_10m) {
}

record CurrentUnits(
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
