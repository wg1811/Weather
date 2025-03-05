package com.weatherhistoryandforecastapp.HowWasTheWeather.weather.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO.DailyForecastDTO;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO.ForecastDTO;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.DTO.HourlyForecastDTO;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.common.Coordinates;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.forecast.ForecastData;
import com.weatherhistoryandforecastapp.HowWasTheWeather.weather.model.historical.WeatherData;

import reactor.core.publisher.Mono;

@Service
public class WeatherService {
        private final WebClient webClientHistory;
        private final WebClient webClientForecast;

        public WeatherService(WebClient.Builder webClientBuilder) {
                this.webClientHistory = webClientBuilder.baseUrl("https://archive-api.open-meteo.com/v1/archive")
                                .build();
                this.webClientForecast = webClientBuilder.baseUrl("https://api.open-meteo.com/v1/forecast").build();
        }

        public Mono<WeatherData> getHistoricalWeather(Coordinates coordinates, String startDate, String endDate) {
                return webClientHistory.get()
                                .uri(uriBuilder -> uriBuilder
                                                .queryParam("latitude", coordinates.lat())
                                                .queryParam("longitude", coordinates.lng())
                                                .queryParam("daily",
                                                                "weather_code,temperature_2m_max,temperature_2m_min,precipitation_sum,pressure_msl_mean,apparent_temperature_max,apparent_temperature_min,precipitation_hours,sunrise,sunset,wind_speed_10m_max,wind_gusts_10m_max,wind_direction_10m_dominant,shortwave_radiation_sum,et0_fao_evapotranspiration")
                                                .queryParam("hourly",
                                                                "temperature_2m,relative_humidity_2m,dew_point_2m,apparent_temperature,precipitation,weather_code,pressure_msl,cloud_cover,visibility,wind_speed_10m,wind_direction_10m,wind_gusts_10m,soil_temperature_0cm,soil_moisture_0_1cm")
                                                .queryParam("timezone", "auto")
                                                .queryParam("start_date", startDate)
                                                .queryParam("end_date", endDate)
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
                                .bodyToMono(WeatherData.class);
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

                return new ForecastDTO(forecastData.requestTime(), forecastData.latitude(), forecastData.longitude(),
                                hourlyForecastDTO, dailyForecastDTO);
        }

        public void GetWeatherTest(Coordinates coordinates, String startDate, String endDate) {
                String url = "https://archive-api.open-meteo.com/v1/archive" +
                                "?latitude=" + coordinates.lat() +
                                "&longitude=" + coordinates.lng() +
                                "&daily=weather_code,temperature_2m_max,temperature_2m_min,precipitation_sum,pressure_msl_mean,apparent_temperature_max,apparent_temperature_min,precipitation_hours,sunrise,sunset,wind_speed_10m_max,wind_gusts_10m_max,wind_direction_10m_dominant,shortwave_radiation_sum,et0_fao_evapotranspiration"
                                +
                                "&hourly=temperature_2m,relative_humidity_2m,dew_point_2m,apparent_temperature,precipitation,weather_code,pressure_msl,cloud_cover,visibility,wind_speed_10m,wind_direction_10m,wind_gusts_10m,soil_temperature_0cm,soil_moisture_0_1cm"
                                +
                                "&timezone=auto" +
                                "&start_date=" + startDate +
                                "&end_date=" + endDate;

                System.out.println("Requesting Open-Meteo: " + url); // Debugging output
        }
}

// class WeatherService
// {
// public async Task<string?> TestWeather(
// double lat,
// double lng,
// string startDate,
// string endDate
// )
// {
// try
// {
// using var client = new HttpClient();
// var weatherUrl =
// $"https://archive-api.open-meteo.com/v1/archive"
// + $"?latitude={lat}&longitude={lng}"
// +
// $"&daily=weather_code,temperature_2m_max,temperature_2m_min,precipitation_sum,pressure_msl_mean,apparent_temperature_max,apparent_temperature_min,precipitation_hours,sunrise,sunset,wind_speed_10m_max,wind_gusts_10m_max,wind_direction_10m_dominant,shortwave_radiation_sum,et0_fao_evapotranspiration"
// +
// $"&hourly=temperature_2m,relative_humidity_2m,dew_point_2m,apparent_temperature,precipitation,weather_code,pressure_msl,cloud_cover,visibility,wind_speed_10m,wind_direction_10m,wind_gusts_10m,soil_temperature_0cm,soil_moisture_0_1cm"
// + $"&timezone=auto"
// + $"&start_date={startDate}"
// + $"&end_date={endDate}";

// var response = await client.GetAsync(weatherUrl);

// return response.IsSuccessStatusCode
// ? await response.Content.ReadAsStringAsync()
// : null;
// }
// catch (Exception ex)
// {
// Console.WriteLine($"An error occurred: {ex.Message}");
// return null;
// }
// }

// public async Task<WeatherResponse?> GetHistoricalWeather(
// double lat,
// double lng,
// string startDate,
// string endDate
// )
// {
// try
// {
// using var client = new HttpClient();
// var weatherUrl =
// $"https://archive-api.open-meteo.com/v1/archive"
// + $"?latitude={lat}&longitude={lng}"
// +
// $"&daily=weather_code,temperature_2m_max,temperature_2m_min,precipitation_sum,pressure_msl_mean,apparent_temperature_max,apparent_temperature_min,precipitation_hours,sunrise,sunset,wind_speed_10m_max,wind_gusts_10m_max,wind_direction_10m_dominant,shortwave_radiation_sum,et0_fao_evapotranspiration"
// +
// $"&hourly=temperature_2m,relative_humidity_2m,dew_point_2m,apparent_temperature,precipitation,weather_code,pressure_msl,cloud_cover,visibility,wind_speed_10m,wind_direction_10m,wind_gusts_10m,soil_temperature_0cm,soil_moisture_0_1cm"
// + $"&timezone=auto"
// + $"&start_date={startDate:yyyy-MM-dd}"
// + $"&end_date={endDate:yyyy-MM-dd}";

// var response = await client.GetAsync(weatherUrl);

// if (!response.IsSuccessStatusCode)
// {
// Console.WriteLine(
// $"API request failed with status code: {response.StatusCode}"
// );
// return null;
// }

// var jsonResponse = await response.Content.ReadAsStringAsync();
// Console.WriteLine($"Raw API response: {jsonResponse}"); // Is it working?

// var options = new JsonSerializerOptions { PropertyNameCaseInsensitive = true
// };

// var weatherResponse = JsonSerializer.Deserialize<WeatherResponse>(
// jsonResponse,
// options
// );

// if (weatherResponse == null)
// {
// Console.WriteLine("Failed to deserialize weather response");
// return null;
// }

// return weatherResponse;
// }
// catch (Exception ex)
// {
// Console.WriteLine($"An error occurred: {ex.Message}");
// return null;
// }
// }
// }
// }
// // This is the json response. I have to map it to the classes
// //
// "{\"latitude\":40.738136,\"longitude\":-74.04254,\"generationtime_ms\":0.2688169479370117,\"utc_offset_seconds\":-18000,\"timezone\":\"America/New_York\",\"timezone_abbreviation\":\"GMT-5\",\"elevation\":32.0,\"daily_units\":{\"time\":\"iso8601\",\"weather_code\":\"wmo
// code\",\"temperature_2m_max\":\"°C\",\"temperature_2m_min\":\"°C\",\"precipitation_sum\":\"mm\",\"pressure_msl_mean\":\"hPa\"},\"daily\":{\"time\":[\"2010-10-10\",\"2010-10-11\",\"2010-10-12\",\"2010-10-13\",\"2010-10-14\",\"2010-10-15\",\"2010-10-16\",\"2010-10-17\"],\"weather_code\":[0,53,63,0,63,51,51,1],\"temperature_2m_max\":[19.9,23.4,19.0,16.7,16.1,14.0,14.1,19.6],\"temperature_2m_min\":[6.7,11.6,12.7,8.4,10.0,9.3,8.9,7.0],\"precipitation_sum\":[0.00,1.20,12.90,0.00,11.20,1.10,0.10,0.00],\"pressure_msl_mean\":[1012.1,1008.6,1009.2,1016.3,1011.2,999.6,1008.0,1011.7]}}"
