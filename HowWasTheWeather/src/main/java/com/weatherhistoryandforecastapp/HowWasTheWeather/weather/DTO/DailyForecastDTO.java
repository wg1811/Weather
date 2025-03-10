package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO;

public record DailyForecastDTO(
                String[] time,
                int[] weather_code,
                double[] temperature_2m_max,
                double[] temperature_2m_min,
                double[] apparent_temperature_max,
                double[] apparent_temperature_min,
                String[] sunrise,
                String[] sunset,
                double[] precipitation_sum,
                double[] rain_sum,
                double[] showers_sum,
                double[] snowfall_sum,
                int[] precipitation_probability_max,
                double[] wind_speed_10m_max,
                double[] wind_gusts_10m_max,
                int[] wind_direction_10m_dominant) {
}

// Records automatically generate a constructor. This was only necessary if I
// wanted to use LocalDateTime instead of String.
// _____________________________________________________________________________________
// public DailyForecastDTO(LocalDateTime[] time, int[] weather_code, double[]
// temperature_2m_max,
// double[] temperature_2m_min, double[] apparent_temperature_max,
// double[] apparent_temperature_min, LocalDateTime[] sunrise, LocalDateTime[]
// sunset,
// double[] precipitation_sum, double[] rain_sum, double[] showers_sum, double[]
// snowfall_sum,
// int[] precipitation_probability_max, double[] wind_speed_10m_max,
// double[] wind_gusts_10m_max, int[] wind_direction_10m_dominant) {
// this(
// convertToStringArray(time),
// weather_code,
// temperature_2m_max,
// temperature_2m_min,
// apparent_temperature_max,
// apparent_temperature_min,
// convertToStringArray(sunrise),
// convertToStringArray(sunset),
// precipitation_sum,
// rain_sum,
// showers_sum,
// snowfall_sum,
// precipitation_probability_max,
// wind_speed_10m_max,
// wind_gusts_10m_max,
// wind_direction_10m_dominant);
// }

// private static String[] convertToStringArray(LocalDateTime[] dateTimes) {
// return java.util.Arrays.stream(dateTimes)
// .map(LocalDateTime::toString)
// .toArray(String[]::new);
// }
