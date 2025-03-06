package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast;

public record ForecastData(
    double latitude,
    double longitude,
    double generationtime_ms,
    int utc_offset_seconds,
    String timezone,
    String timezone_abbreviation,
    double elevation,
    CurrentWeatherData current_weather,
    HourlyForecastData hourly,
    DailyForecastData daily
) {}
