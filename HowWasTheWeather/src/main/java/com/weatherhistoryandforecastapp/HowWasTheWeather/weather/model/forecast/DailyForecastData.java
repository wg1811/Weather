package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast;

public record DailyForecastData(
        String[] time,
        int[] weather_code,
        double[] temperature_2m_max,
        double[] temperature_2m_min,
        double[] apparent_temperature_max,
        double[] apparent_temperature_min,
        String[] sunrise,
        String[] sunset,
        double[] daylight_duration,
        double[] sunshine_duration,
        double[] uv_index_max,
        double[] uv_index_clear_sky_max,
        double[] precipitation_sum,
        double[] rain_sum,
        double[] showers_sum,
        double[] snowfall_sum,
        double[] precipitation_hours,
        int[] precipitation_probability_max,
        double[] wind_speed_10m_max,
        double[] wind_gusts_10m_max,
        int[] wind_direction_10m_dominant) {
}