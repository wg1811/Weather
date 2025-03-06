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
                CurrentWeatherData current,
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
