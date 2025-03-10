package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast;

public record HourlyForecastData(
                String[] time,
                double[] temperature_2m,
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
                double[] uv_index,
                int[] is_day,
                int[] sunshine_duration) {
}

record HourlyForecastUnits(
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
