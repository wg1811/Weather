package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast;

public record DailyForecastUnits(
        String time,
        String weather_code,
        String temperature_2m_max,
        String temperature_2m_min,
        String apparent_temperature_max,
        String apparent_temperature_min,
        String sunrise,
        String sunset,
        String daylight_duration,
        String sunshine_duration,
        String uv_index_max,
        String uv_index_clear_sky_max,
        String precipitation_sum,
        String rain_sum,
        String showers_sum,
        String snowfall_sum,
        String precipitation_hours,
        String precipitation_probability_max,
        String wind_speed_10m_max,
        String wind_gusts_10m_max,
        String wind_direction_10m_dominant) {
}
