package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO;

public record ForecastDTO(
                double latitude,
                double longitude,
                CurrentWeatherDTO currentDTO,
                HourlyForecastDTO hourlyDTO,
                DailyForecastDTO dailyDTO) {

        public CurrentWeatherDTO getCurrentWeather() {
                return currentDTO;
        }

        public HourlyForecastDTO getHourlyForecast() {
                return hourlyDTO;
        }

        public DailyForecastDTO getDailyForecast() {
                return dailyDTO;
        }
}
