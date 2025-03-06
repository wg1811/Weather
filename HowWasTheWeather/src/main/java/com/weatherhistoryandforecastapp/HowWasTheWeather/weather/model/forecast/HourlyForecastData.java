package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast;

public record HourlyForecastData(
    String[] time,
    double[] temperature_2m,
    int[] relative_humidity_2m,
    double[] dew_point_2m,
    double[] apparent_temperature,
    int[] precipitation_probability,
    double[] precipitation,
    double[] rain,
    double[] showers,
    double[] snowfall,
    double[] snow_depth,
    int[] weather_code,
    double[] pressure_msl,
    double[] surface_pressure,
    int[] cloud_cover,
    double[] visibility,
    double[] wind_speed_10m,
    int[] wind_direction_10m,
    double[] wind_gusts_10m,
    double[] uv_index,
    int[] is_day,
    int[] sunshine_duration,
    double[] cape,
    double[] freezing_level_height
) {}