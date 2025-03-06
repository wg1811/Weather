package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO;

import java.time.LocalDateTime;

public record ForecastDTO(
        String startDate,
        // String location, // not sure about this? Maybe it should be a Coordinates
        // object? Or maybe it
        // // should be a string with the city name?
        double latitude,
        double longitude,
        DailyForecastDTO dailyDTO,
        HourlyForecastDTO hourlyDTO) {

    public ForecastDTO(LocalDateTime startDate, double latitude, double longitude,
            DailyForecastDTO dailyDTO, HourlyForecastDTO hourlyDTO) {
        this(
                startDate.toString(),
                // location,
                latitude,
                longitude,
                dailyDTO,
                hourlyDTO);
    }

    public ForecastDTO(LocalDateTime requestTime, double latitude, double longitude,
            HourlyForecastDTO hourlyDTO, DailyForecastDTO dailyDTO) {
        this(requestTime.toString(), latitude, longitude, dailyDTO, hourlyDTO);
    }
}
