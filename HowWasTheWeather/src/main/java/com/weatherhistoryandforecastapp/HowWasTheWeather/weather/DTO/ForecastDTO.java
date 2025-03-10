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

// Records automatically generate a constructor. This was only necessary if I
// wanted to use LocalDateTime instead of String.
// _____________________________________________________________________________________
// public ForecastDTO(String startDate, double latitude, double longitude,
// DailyForecastDTO dailyDTO, HourlyForecastDTO hourlyDTO) {
// this(
// startDate,
// // location,
// latitude,
// longitude,
// dailyDTO,
// hourlyDTO);
// }

// public ForecastDTO(String requestTime, double latitude, double longitude,
// HourlyForecastDTO hourlyDTO, DailyForecastDTO dailyDTO) {
// this(requestTime.toString(), latitude, longitude, dailyDTO, hourlyDTO);
// }
// }
