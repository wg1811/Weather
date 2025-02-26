package com.weatherhistoryandforecastapp.HowWasTheWeather.weather;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

public record WeatherData(
    int id,
    double latitude,
    double longitude,
    String timezone,
    double elevation,
    double utc_Offset_Seconds,
    double generationTime_Ms,
    @NotNull    LocalDateTime startDate,
    @NotNull    LocalDateTime endDate,
    DailyUnits dailyUnits,
    HourlyUnits hourlyUnits,
    DailyData daily,
    HourlyData hourly
) { } 