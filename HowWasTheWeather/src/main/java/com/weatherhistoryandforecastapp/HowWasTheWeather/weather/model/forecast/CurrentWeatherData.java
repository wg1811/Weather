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
