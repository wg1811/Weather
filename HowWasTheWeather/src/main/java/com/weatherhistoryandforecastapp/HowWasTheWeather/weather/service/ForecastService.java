package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO.CurrentWeatherDTO;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO.DailyForecastDTO;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO.ForecastDTO;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO.HourlyForecastDTO;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.common.Coordinates;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast.ForecastData;

import reactor.core.publisher.Mono;

@Service
public class ForecastService {
        private final WebClient webClientForecast;

        public ForecastService(WebClient.Builder webClientBuilder) {
                this.webClientForecast = webClientBuilder.baseUrl("https://api.open-meteo.com/v1/forecast").build();
        }

        // Getting Weather Forecast Data from Open-Meteo
        public Mono<ForecastDTO> getWeatherForecast(Coordinates coordinates) {
                return webClientForecast.get()
                                .uri(uriBuilder -> uriBuilder
                                                .queryParam("latitude", coordinates.lat())
                                                .queryParam("longitude", coordinates.lng())
                                                .queryParam("current",
                                                                "temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,rain,showers,snowfall,weather_code,cloud_cover,wind_speed_10m,wind_direction_10m,wind_gusts_10m")
                                                .queryParam("hourly",
                                                                "temperature_2m,apparent_temperature,precipitation_probability,precipitation,rain,showers,snowfall,snow_depth,weather_code,pressure_msl,surface_pressure,cloud_cover,visibility,wind_speed_10m,wind_direction_10m,uv_index,is_day,sunshine_duration")
                                                .queryParam("daily",
                                                                "weather_code,temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min,sunrise,sunset,daylight_duration,sunshine_duration,uv_index_max,uv_index_clear_sky_max,precipitation_sum,rain_sum,showers_sum,snowfall_sum,precipitation_hours,precipitation_probability_max,wind_speed_10m_max,wind_gusts_10m_max,wind_direction_10m_dominant")
                                                .queryParam("forecast_days", 16)
                                                .queryParam("models", "best_match")
                                                .queryParam("timezone", "auto") // Kept from original implementation
                                                .build())
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                                                response -> {
                                                        if (response.statusCode().equals(HttpStatusCode.valueOf(404))) {
                                                                return Mono.error(new ResponseStatusException(
                                                                                HttpStatus.NOT_FOUND,
                                                                                "No weather data found for the given coordinates."));
                                                        } else {
                                                                return Mono.error(
                                                                                new ResponseStatusException(
                                                                                                HttpStatus.BAD_REQUEST,
                                                                                                "Failed to get weather data."));
                                                        }
                                                })
                                .bodyToMono(ForecastData.class)
                                .map(this::convertToForecastDTO);
        }
        // End of getting forecast

        // Convert ForecastData to ForecastDTO
        private ForecastDTO convertToForecastDTO(ForecastData forecastData) {
                var currentWeatherDTO = new CurrentWeatherDTO(
                                forecastData.current().time(),
                                forecastData.current().temperature_2m(),
                                forecastData.current().relative_humidity_2m(),
                                forecastData.current().apparent_temperature(),
                                forecastData.current().is_day(),
                                forecastData.current().precipitation(),
                                forecastData.current().rain(),
                                forecastData.current().showers(),
                                forecastData.current().snowfall(),
                                forecastData.current().weather_code(),
                                forecastData.current().cloud_cover(),
                                forecastData.current().wind_speed_10m(),
                                forecastData.current().wind_direction_10m(),
                                forecastData.current().wind_gusts_10m());
                var hourlyForecastDTO = new HourlyForecastDTO(forecastData.hourly().time(),
                                forecastData.hourly().temperature_2m(), forecastData.hourly().apparent_temperature(),
                                forecastData.hourly().precipitation_probability(),
                                forecastData.hourly().precipitation(),
                                forecastData.hourly().rain(), forecastData.hourly().showers(),
                                forecastData.hourly().snowfall(),
                                forecastData.hourly().weather_code(), forecastData.hourly().cloud_cover(),
                                forecastData.hourly().wind_speed_10m(), forecastData.hourly().wind_direction_10m());

                var dailyForecastDTO = new DailyForecastDTO(forecastData.daily().time(),
                                forecastData.daily().weather_code(),
                                forecastData.daily().temperature_2m_max(), forecastData.daily().temperature_2m_min(),
                                forecastData.daily().apparent_temperature_max(),
                                forecastData.daily().apparent_temperature_min(),
                                forecastData.daily().sunrise(), forecastData.daily().sunset(),
                                forecastData.daily().precipitation_sum(), forecastData.daily().rain_sum(),
                                forecastData.daily().showers_sum(), forecastData.daily().snowfall_sum(),
                                forecastData.daily().precipitation_probability_max(),
                                forecastData.daily().wind_speed_10m_max(), forecastData.daily().wind_gusts_10m_max(),
                                forecastData.daily().wind_direction_10m_dominant());

                return new ForecastDTO(forecastData.latitude(), forecastData.longitude(), currentWeatherDTO,
                                hourlyForecastDTO, dailyForecastDTO);
        }
}
// End of converting ForecastData to ForecastDTO
