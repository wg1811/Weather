/**
 * Interface for current weather data
 */
export interface CurrentWeatherDTO {
  time: string;
  temperature_2m: number;
  relative_humidity_2m: number;
  apparent_temperature: number;
  is_day: number;
  precipitation: number;
  rain: number;
  showers: number;
  snowfall: number;
  weather_code: number;
  cloud_cover: number;
  wind_speed_10m: number;
  wind_direction_10m: number;
  wind_gusts_10m: number;
}

/**
 * Interface for hourly forecast data
 */
export interface HourlyForecastDTO {
  time: string[];
  temperature_2m: number[];
  apparent_temperature: number[];
  precipitation_probability: number[];
  precipitation: number[];
  rain: number[];
  showers: number[];
  snowfall: number[];
  weather_code: number[];
  cloud_cover: number[];
  wind_speed_10m: number[];
  wind_direction_10m: number[];
}

/**
 * Interface for daily forecast data
 */
export interface DailyForecastDTO {
  time: string[];
  weather_code: number[];
  temperature_2m_max: number[];
  temperature_2m_min: number[];
  apparent_temperature_max: number[];
  apparent_temperature_min: number[];
  sunrise: string[];
  sunset: string[];
  precipitation_sum: number[];
  rain_sum: number[];
  showers_sum: number[];
  snowfall_sum: number[];
  precipitation_probability_max: number[];
  wind_speed_10m_max: number[];
  wind_gusts_10m_max: number[];
  wind_direction_10m_dominant: number[];
}

/**
 * Interface for the main forecast data which combines current, hourly and daily data
 */
export interface ForecastDTO {
  latitude: number;
  longitude: number;
  currentDTO: CurrentWeatherDTO;
  hourlyDTO: HourlyForecastDTO;
  dailyDTO: DailyForecastDTO;
}

/**
 * Weather code to description mapping
 * (Add actual weather code mappings based on your needs)
 */
export enum WeatherCode {
  CLEAR_SKY = 0,
  MAINLY_CLEAR = 1,
  PARTLY_CLOUDY = 2,
  OVERCAST = 3,
  FOG = 45,
  DEPOSITING_RIME_FOG = 48,
  LIGHT_DRIZZLE = 51,
  MODERATE_DRIZZLE = 53,
  DENSE_DRIZZLE = 55,
  LIGHT_FREEZING_DRIZZLE = 56,
  DENSE_FREEZING_DRIZZLE = 57,
  SLIGHT_RAIN = 61,
  MODERATE_RAIN = 63,
  HEAVY_RAIN = 65,
  LIGHT_FREEZING_RAIN = 66,
  HEAVY_FREEZING_RAIN = 67,
  SLIGHT_SNOW_FALL = 71,
  MODERATE_SNOW_FALL = 73,
  HEAVY_SNOW_FALL = 75,
  SNOW_GRAINS = 77,
  SLIGHT_RAIN_SHOWERS = 80,
  MODERATE_RAIN_SHOWERS = 81,
  VIOLENT_RAIN_SHOWERS = 82,
  SLIGHT_SNOW_SHOWERS = 85,
  HEAVY_SNOW_SHOWERS = 86,
  THUNDERSTORM = 95,
  THUNDERSTORM_WITH_SLIGHT_HAIL = 96,
  THUNDERSTORM_WITH_HEAVY_HAIL = 99,
}

// Interface for weather details including description and icon

export interface WeatherDetails {
  description: string;
  icon: string;
}

/**
 * Weather mapping object that maps weather codes to their descriptions and icons
 */
export const weatherMapping: Record<number, WeatherDetails> = {
  [WeatherCode.CLEAR_SKY]: { description: "Clear sky", icon: "☀️" },
  [WeatherCode.MAINLY_CLEAR]: { description: "Mainly clear", icon: "🌤️" },
  [WeatherCode.PARTLY_CLOUDY]: { description: "Partly cloudy", icon: "⛅" },
  [WeatherCode.OVERCAST]: { description: "Overcast", icon: "☁️" },
  [WeatherCode.FOG]: { description: "Fog", icon: "🌫️" },
  [WeatherCode.DEPOSITING_RIME_FOG]: {
    description: "Depositing rime fog",
    icon: "🌫️",
  },
  [WeatherCode.LIGHT_DRIZZLE]: { description: "Light drizzle", icon: "🌦️" },
  [WeatherCode.MODERATE_DRIZZLE]: {
    description: "Moderate drizzle",
    icon: "🌦️",
  },
  [WeatherCode.DENSE_DRIZZLE]: { description: "Dense drizzle", icon: "🌧️" },
  [WeatherCode.LIGHT_FREEZING_DRIZZLE]: {
    description: "Light freezing drizzle",
    icon: "🌨️",
  },
  [WeatherCode.DENSE_FREEZING_DRIZZLE]: {
    description: "Dense freezing drizzle",
    icon: "🌨️",
  },
  [WeatherCode.SLIGHT_RAIN]: { description: "Slight rain", icon: "🌦️" },
  [WeatherCode.MODERATE_RAIN]: { description: "Moderate rain", icon: "🌧️" },
  [WeatherCode.HEAVY_RAIN]: { description: "Heavy rain", icon: "🌧️" },
  [WeatherCode.LIGHT_FREEZING_RAIN]: {
    description: "Light freezing rain",
    icon: "🌨️",
  },
  [WeatherCode.HEAVY_FREEZING_RAIN]: {
    description: "Heavy freezing rain",
    icon: "🌨️",
  },
  [WeatherCode.SLIGHT_SNOW_FALL]: {
    description: "Slight snow fall",
    icon: "🌨️",
  },
  [WeatherCode.MODERATE_SNOW_FALL]: {
    description: "Moderate snow fall",
    icon: "❄️",
  },
  [WeatherCode.HEAVY_SNOW_FALL]: { description: "Heavy snow fall", icon: "❄️" },
  [WeatherCode.SNOW_GRAINS]: { description: "Snow grains", icon: "❄️" },
  [WeatherCode.SLIGHT_RAIN_SHOWERS]: {
    description: "Slight rain showers",
    icon: "🌦️",
  },
  [WeatherCode.MODERATE_RAIN_SHOWERS]: {
    description: "Moderate rain showers",
    icon: "🌧️",
  },
  [WeatherCode.VIOLENT_RAIN_SHOWERS]: {
    description: "Violent rain showers",
    icon: "⛈️",
  },
  [WeatherCode.SLIGHT_SNOW_SHOWERS]: {
    description: "Slight snow showers",
    icon: "🌨️",
  },
  [WeatherCode.HEAVY_SNOW_SHOWERS]: {
    description: "Heavy snow showers",
    icon: "❄️",
  },
  [WeatherCode.THUNDERSTORM]: { description: "Thunderstorm", icon: "⛈️" },
  [WeatherCode.THUNDERSTORM_WITH_SLIGHT_HAIL]: {
    description: "Thunderstorm with slight hail",
    icon: "⛈️",
  },
  [WeatherCode.THUNDERSTORM_WITH_HEAVY_HAIL]: {
    description: "Thunderstorm with heavy hail",
    icon: "⛈️",
  },
};
/**
 * Helper function to get weather details from code
 */
export function getWeatherDetails(code: number): WeatherDetails {
  return weatherMapping[code] || { description: "Unknown", icon: "❓" };
}

/**
 * Helper type for a single hourly forecast entry
 */
export interface HourlyForecastEntry {
  time: string;
  temperature_2m: number;
  apparent_temperature: number;
  precipitation_probability: number;
  precipitation: number;
  rain: number;
  showers: number;
  snowfall: number;
  weather_code: number;
  cloud_cover: number;
  wind_speed_10m: number;
  wind_direction_10m: number;
}

/**
 * Helper type for a single daily forecast entry
 */
export interface DailyForecastEntry {
  time: string;
  weather_code: number;
  temperature_2m_max: number;
  temperature_2m_min: number;
  apparent_temperature_max: number;
  apparent_temperature_min: number;
  sunrise: string;
  sunset: string;
  precipitation_sum: number;
  rain_sum: number;
  showers_sum: number;
  snowfall_sum: number;
  precipitation_probability_max: number;
  wind_speed_10m_max: number;
  wind_gusts_10m_max: number;
  wind_direction_10m_dominant: number;
}
