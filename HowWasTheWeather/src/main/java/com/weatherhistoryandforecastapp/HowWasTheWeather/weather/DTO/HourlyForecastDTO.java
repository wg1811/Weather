package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO;

public record HourlyForecastDTO(
                String[] time,
                double[] temperature_2m,
                double[] apparent_temperature,
                int[] precipitation_probability,
                double[] precipitation,
                double[] rain,
                double[] showers,
                double[] snowfall,
                int[] weather_code,
                int[] cloud_cover,
                double[] wind_speed_10m,
                int[] wind_direction_10m) {
}
