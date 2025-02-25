package com.weatherhistoryandforecastapp.HowWasTheWeather.weather;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;

public record Weather(
    int id,
    double latitude,
    double longitude,
    // String timezone,
    // double elevation,
    //  double utc_Offset_Seconds,
    //  double generationTime_Ms,
    @NotEmpty
    LocalDateTime startDate,
    @NotEmpty
    LocalDateTime endDate
    //  DailyUnits dailyUnits,
    //  HourlyUnits hourlyUnits,
    //  DailyData daily,
    //  HourlyData hourly


) {
} 
