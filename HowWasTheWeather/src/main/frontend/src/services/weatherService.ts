import { forecastApi } from "../api/forecastApi";
import {
  ForecastDTO,
  DailyForecastEntry,
  HourlyForecastEntry,
} from "../types/weather";

export const processDailyForecast = (
  forecastData: ForecastDTO
): DailyForecastEntry[] => {
  if (!forecastData?.dailyDTO) return [];

  // Convert array-based DTO to array of objects for easier use
  return forecastData.dailyDTO.time.map((time, index) => ({
    time,
    weather_code: forecastData.dailyDTO.weather_code[index],
    temperature_2m_max: forecastData.dailyDTO.temperature_2m_max[index],
    temperature_2m_min: forecastData.dailyDTO.temperature_2m_min[index],
    apparent_temperature_max:
      forecastData.dailyDTO.apparent_temperature_max[index],
    apparent_temperature_min:
      forecastData.dailyDTO.apparent_temperature_min[index],
    sunrise: forecastData.dailyDTO.sunrise[index],
    sunset: forecastData.dailyDTO.sunset[index],
    precipitation_sum: forecastData.dailyDTO.precipitation_sum[index],
    rain_sum: forecastData.dailyDTO.rain_sum[index],
    showers_sum: forecastData.dailyDTO.showers_sum[index],
    snowfall_sum: forecastData.dailyDTO.snowfall_sum[index],
    precipitation_probability_max:
      forecastData.dailyDTO.precipitation_probability_max[index],
    wind_speed_10m_max: forecastData.dailyDTO.wind_speed_10m_max[index],
    wind_gusts_10m_max: forecastData.dailyDTO.wind_gusts_10m_max[index],
    wind_direction_10m_dominant:
      forecastData.dailyDTO.wind_direction_10m_dominant[index],
  }));
};

// Process hourly forecast data for the next 24 hours
export const processHourlyForecast = (
  forecastData: ForecastDTO
): HourlyForecastEntry[] => {
  if (!forecastData?.hourlyDTO) return [];

  // Take the first 24 entries (24 hours) from the hourly forecast
  return forecastData.hourlyDTO.time.slice(0, 24).map((time, index) => ({
    time,
    temperature_2m: forecastData.hourlyDTO.temperature_2m[index],
    apparent_temperature: forecastData.hourlyDTO.apparent_temperature[index],
    precipitation_probability:
      forecastData.hourlyDTO.precipitation_probability[index],
    precipitation: forecastData.hourlyDTO.precipitation[index],
    rain: forecastData.hourlyDTO.rain[index],
    showers: forecastData.hourlyDTO.showers[index],
    snowfall: forecastData.hourlyDTO.snowfall[index],
    weather_code: forecastData.hourlyDTO.weather_code[index],
    cloud_cover: forecastData.hourlyDTO.cloud_cover[index],
    wind_speed_10m: forecastData.hourlyDTO.wind_speed_10m[index],
    wind_direction_10m: forecastData.hourlyDTO.wind_direction_10m[index],
  }));
};

// New function: Process all hourly forecast data (16 days)
export const processFullHourlyForecast = (
  forecastData: ForecastDTO
): HourlyForecastEntry[] => {
  if (!forecastData?.hourlyDTO) return [];

  return forecastData.hourlyDTO.time.map((time, index) => ({
    time,
    temperature_2m: forecastData.hourlyDTO.temperature_2m[index],
    apparent_temperature: forecastData.hourlyDTO.apparent_temperature[index],
    precipitation_probability:
      forecastData.hourlyDTO.precipitation_probability[index],
    precipitation: forecastData.hourlyDTO.precipitation[index],
    rain: forecastData.hourlyDTO.rain[index],
    showers: forecastData.hourlyDTO.showers[index],
    snowfall: forecastData.hourlyDTO.snowfall[index],
    weather_code: forecastData.hourlyDTO.weather_code[index],
    cloud_cover: forecastData.hourlyDTO.cloud_cover[index],
    wind_speed_10m: forecastData.hourlyDTO.wind_speed_10m[index],
    wind_direction_10m: forecastData.hourlyDTO.wind_direction_10m[index],
  }));
};

// Weather service
export const weatherService = {
  getForecast: async (locationName: string): Promise<ForecastDTO> => {
    return await forecastApi.getForecast(locationName);
  },
};
