package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast;

public record CurrentWeatherData(
    String time,
    double temperature_2m,
    int relative_humidity_2m,
    double apparent_temperature,
    int is_day,
    double precipitation,
    double rain,
    double showers,
    double snowfall,
    int weather_code,
    int cloud_cover,
    double pressure_msl,
    double surface_pressure,
    double wind_speed_10m,
    int wind_direction_10m,
    double wind_gusts_10m
) {}