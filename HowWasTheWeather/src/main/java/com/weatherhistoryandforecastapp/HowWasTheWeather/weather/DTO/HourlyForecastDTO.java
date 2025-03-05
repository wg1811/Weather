package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO;

import java.time.LocalDateTime;

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

        public HourlyForecastDTO(LocalDateTime[] time, double[] temperature_2m,
                        double[] apparent_temperature, int[] precipitation_probability, double[] precipitation,
                        double[] rain, double[] showers, double[] snowfall, int[] weather_code, int[] cloud_cover,
                        double[] wind_speed_10m, int[] wind_direction_10m) {
                this(
                                convertToStringArray(time),
                                temperature_2m,
                                apparent_temperature,
                                precipitation_probability,
                                precipitation,
                                rain,
                                showers,
                                snowfall,
                                weather_code,
                                cloud_cover,
                                wind_speed_10m,
                                wind_direction_10m);
        }

        private static String[] convertToStringArray(LocalDateTime[] dateTimes) {
                return java.util.Arrays.stream(dateTimes)
                                .map(LocalDateTime::toString)
                                .toArray(String[]::new);
        }
}
