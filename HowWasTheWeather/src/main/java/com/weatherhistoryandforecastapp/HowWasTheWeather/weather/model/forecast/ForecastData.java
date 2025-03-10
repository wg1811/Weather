package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast;

import jakarta.validation.constraints.NotNull;

public record ForecastData(
                int id,
                double latitude,
                double longitude,
                String timezone,
                double elevation,
                double utc_Offset_Seconds,
                double generationTime_Ms,
                @NotNull String requestTime,
                CurrentWeatherData current,
                CurrentUnits currentUnits,
                DailyForecastUnits dailyUnits,
                HourlyForecastUnits hourlyUnits,
                DailyForecastData daily,
                HourlyForecastData hourly) {

}
