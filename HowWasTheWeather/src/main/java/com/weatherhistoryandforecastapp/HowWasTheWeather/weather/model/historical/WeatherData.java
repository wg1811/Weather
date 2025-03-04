package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.historical;

import java.time.LocalDateTime;

import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.common.Coordinates;

import jakarta.validation.constraints.NotNull;

public record WeatherData(
        int id,
        double latitude,
        double longitude,
        String timezone,
        double elevation,
        double utc_Offset_Seconds,
        double generationTime_Ms,
        @NotNull LocalDateTime startDate,
        @NotNull LocalDateTime endDate,
        DailyUnits dailyUnits,
        HourlyUnits hourlyUnits,
        DailyData daily,
        HourlyData hourly) {

    public WeatherData(Coordinates coordinates, String startDate, String endDate) {
        this(0,
                coordinates.lat(),
                coordinates.lng(),
                null,
                0.0,
                0.0,
                0.0,
                LocalDateTime.parse(startDate),
                LocalDateTime.parse(endDate),
                null,
                null,
                null,
                null);
    }
}