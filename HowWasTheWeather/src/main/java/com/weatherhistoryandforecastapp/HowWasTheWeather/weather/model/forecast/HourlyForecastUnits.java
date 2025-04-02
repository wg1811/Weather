package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast;

public record HourlyForecastUnits(
        String time,
        String temperature_2m,
        String apparent_temperature,
        String precipitation_probability,
        String precipitation,
        String rain,
        String showers,
        String snowfall,
        String snow_depth,
        String weather_code,
        String pressure_msl,
        String surface_pressure,
        String cloud_cover,
        String visibility,
        String wind_speed_10m,
        String wind_direction_10m,
        String uv_index,
        String is_day,
        String sunshine_duration) {
}
