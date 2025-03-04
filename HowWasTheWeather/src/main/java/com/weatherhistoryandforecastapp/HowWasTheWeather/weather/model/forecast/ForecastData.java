package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast;

import java.time.LocalDateTime;

import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.common.Coordinates;

import jakarta.validation.constraints.NotNull;

public record ForecastData(
                int id,
                double latitude,
                double longitude,
                String timezone,
                double elevation,
                double utc_Offset_Seconds,
                double generationTime_Ms,
                @NotNull LocalDateTime requestTime,
                CurrentData current,
                CurrentUnits currentUnits,
                DailyForecastUnits dailyUnits,
                HourlyForecastUnits hourlyUnits,
                DailyForecastData daily,
                HourlyForecastData hourly) {

        public ForecastData(Coordinates coordinates) {
                this(0,
                                coordinates.lat(),
                                coordinates.lng(),
                                null,
                                0.0,
                                0.0,
                                0.0,
                                LocalDateTime.now(),
                                null,
                                null,
                                null,
                                null,
                                null,
                                null);
        }
}

// Additional classes needed based on the API parameters

record CurrentData(
                double temperature_2m,
                double relative_humidity_2m,
                double apparent_temperature,
                int is_day,
                double precipitation,
                double rain,
                double showers,
                double snowfall,
                int weather_code,
                int cloud_cover,
                double wind_speed_10m,
                int wind_direction_10m,
                double wind_gusts_10m) {
}

record CurrentUnits(
                String temperature_2m,
                String relative_humidity_2m,
                String apparent_temperature,
                String is_day,
                String precipitation,
                String rain,
                String showers,
                String snowfall,
                String weather_code,
                String cloud_cover,
                String wind_speed_10m,
                String wind_direction_10m,
                String wind_gusts_10m) {
}

// If not already defined elsewhere, you'll need these classes as well:

// public record HourlyData(
// LocalDateTime[] time,
// double[] temperature_2m,
// double[] apparent_temperature,
// int[] precipitation_probability,
// double[] precipitation,
// double[] rain,
// double[] showers,
// double[] snowfall,
// double[] snow_depth,
// int[] weather_code,
// double[] pressure_msl,
// double[] surface_pressure,
// int[] cloud_cover,
// double[] visibility,
// double[] wind_speed_10m,
// int[] wind_direction_10m,
// double[] uv_index,
// int[] is_day,
// int[] sunshine_duration) {
// }

// public record HourlyUnits(
// String time,
// String temperature_2m,
// String apparent_temperature,
// String precipitation_probability,
// String precipitation,
// String rain,
// String showers,
// String snowfall,
// String snow_depth,
// String weather_code,
// String pressure_msl,
// String surface_pressure,
// String cloud_cover,
// String visibility,
// String wind_speed_10m,
// String wind_direction_10m,
// String uv_index,
// String is_day,
// String sunshine_duration) {
// }

// public record DailyData(
// LocalDateTime[] time,
// int[] weather_code,
// double[] temperature_2m_max,
// double[] temperature_2m_min,
// double[] apparent_temperature_max,
// double[] apparent_temperature_min,
// LocalDateTime[] sunrise,
// LocalDateTime[] sunset,
// double[] daylight_duration,
// double[] sunshine_duration,
// double[] uv_index_max,
// double[] uv_index_clear_sky_max,
// double[] precipitation_sum,
// double[] rain_sum,
// double[] showers_sum,
// double[] snowfall_sum,
// double[] precipitation_hours,
// int[] precipitation_probability_max,
// double[] wind_speed_10m_max,
// double[] wind_gusts_10m_max,
// int[] wind_direction_10m_dominant) {
// }

// public record DailyUnits(
// String time,
// String weather_code,
// String temperature_2m_max,
// String temperature_2m_min,
// String apparent_temperature_max,
// String apparent_temperature_min,
// String sunrise,
// String sunset,
// String daylight_duration,
// String sunshine_duration,
// String uv_index_max,
// String uv_index_clear_sky_max,
// String precipitation_sum,
// String rain_sum,
// String showers_sum,
// String snowfall_sum,
// String precipitation_hours,
// String precipitation_probability_max,
// String wind_speed_10m_max,
// String wind_gusts_10m_max,
// String wind_direction_10m_dominant) {
// }