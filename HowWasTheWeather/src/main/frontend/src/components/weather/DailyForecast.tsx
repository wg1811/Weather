import React from "react";
import { format } from "date-fns";

// Map weather codes to descriptions and icons
type WeatherMapping = {
  [key: number]: { description: string; icon: string };
};

const getWeatherInfo = (code: number) => {
  const weatherMapping: WeatherMapping = {
    0: { description: 'Clear sky', icon: '☀️' },
    1: { description: 'Mainly clear', icon: '🌤️' },
    2: { description: 'Partly cloudy', icon: '⛅' },
    3: { description: 'Overcast', icon: '☁️' },
    45: { description: 'Fog', icon: '🌫️' },
    48: { description: 'Depositing rime fog', icon: '🌫️' },
    51: { description: 'Light drizzle', icon: '🌦️' },
    53: { description: 'Moderate drizzle', icon: '🌦️' },
    55: { description: 'Dense drizzle', icon: '🌧️' },
    56: { description: 'Light freezing drizzle', icon: '🌨️' },
    57: { description: 'Dense freezing drizzle', icon: '🌨️' },
    61: { description: 'Slight rain', icon: '🌦️' },
    63: { description: 'Moderate rain', icon: '🌧️' },
    65: { description: 'Heavy rain', icon: '🌧️' },
    66: { description: 'Light freezing rain', icon: '🌨️' },
    67: { description: 'Heavy freezing rain', icon: '🌨️' },
    71: { description: 'Slight snow fall', icon: '🌨️' },
    73: { description: 'Moderate snow fall', icon: '❄️' },
    75: { description: 'Heavy snow fall', icon: '❄️' },
    77: { description: 'Snow grains', icon: '❄️' },
    80: { description: 'Slight rain showers', icon: '🌦️' },
    81: { description: 'Moderate rain showers', icon: '🌧️' },
    82: { description: 'Violent rain showers', icon: '⛈️' },
    85: { description: 'Slight snow showers', icon: '🌨️' },
    86: { description: 'Heavy snow showers', icon: '❄️' },
    95: { description: 'Thunderstorm', icon: '⛈️' },
    96: { description: 'Thunderstorm with slight hail', icon: '⛈️' },
    99: { description: 'Thunderstorm with heavy hail', icon: '⛈️' },
  };

  return weatherMapping[code] || { description: "Unknown", icon: "unknown" };
};

// Format wind direction
const getWindDirection = (degrees: number) => {
  const directions = ["N", "NE", "E", "SE", "S", "SW", "W", "NW"];
  const index = Math.round(degrees / 45) % 8;
  return directions[index];
};

const DailyForecast = ({ forecastData }) => {
  // Early return if data is not loaded yet
  if (!forecastData || !forecastData.time) {
    return <div className="p-4 text-center">Loading forecast data...</div>;
  }

  return (
    <div className="p-4">
      <h2 className="text-2xl font-bold mb-4">16-Day Forecast</h2>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        {forecastData.time.map((time, index) => {
          const date = new Date(time);
          const weatherInfo = getWeatherInfo(forecastData.weather_code[index]);

          return (
            <div
              key={time}
              className="bg-white rounded-lg shadow-md p-4 hover:shadow-lg transition-shadow"
            >
              <div className="flex justify-between items-center mb-2">
                <h3 className="font-bold">{format(date, "EEE, MMM d")}</h3>
                <div className="text-sm text-gray-500">
                  {format(date, "yyyy")}
                </div>
              </div>

              <div className="flex items-center mb-3">
                <img
                  src={`/assets/weather-icons/${weatherInfo.icon}.svg`}
                  alt={weatherInfo.description}
                  className="w-12 h-12 mr-3"
                />
                <div>
                  <div className="font-medium">{weatherInfo.description}</div>
                  <div className="text-sm text-gray-600">
                    {forecastData.precipitation_probability_max[index]}% chance
                    of precipitation
                  </div>
                </div>
              </div>

              <div className="grid grid-cols-2 gap-2 mb-3">
                <div>
                  <div className="text-sm text-gray-500">High</div>
                  <div className="font-bold text-lg">
                    {forecastData.temperature_2m_max[index]}°C
                  </div>
                  <div className="text-xs text-gray-500">
                    Feels like {forecastData.apparent_temperature_max[index]}°C
                  </div>
                </div>
                <div>
                  <div className="text-sm text-gray-500">Low</div>
                  <div className="font-bold text-lg">
                    {forecastData.temperature_2m_min[index]}°C
                  </div>
                  <div className="text-xs text-gray-500">
                    Feels like {forecastData.apparent_temperature_min[index]}°C
                  </div>
                </div>
              </div>

              <div className="border-t pt-2">
                <div className="grid grid-cols-2 gap-2 text-sm">
                  <div>
                    <div className="text-gray-500">Sunrise</div>
                    <div>
                      {format(new Date(forecastData.sunrise[index]), "h:mm a")}
                    </div>
                  </div>
                  <div>
                    <div className="text-gray-500">Sunset</div>
                    <div>
                      {format(new Date(forecastData.sunset[index]), "h:mm a")}
                    </div>
                  </div>
                </div>
              </div>

              <div className="mt-2 text-sm">
                <div className="flex justify-between">
                  <div>
                    <span className="text-gray-500">Wind:</span>{" "}
                    {forecastData.wind_speed_10m_max[index]} km/h
                    {getWindDirection(
                      forecastData.wind_direction_10m_dominant[index]
                    )}
                  </div>
                  <div>
                    <span className="text-gray-500">Gusts:</span>{" "}
                    {forecastData.wind_gusts_10m_max[index]} km/h
                  </div>
                </div>

                <div className="flex justify-between mt-1">
                  <div>
                    <span className="text-gray-500">Rain:</span>{" "}
                    {forecastData.rain_sum[index]} mm
                  </div>
                  <div>
                    <span className="text-gray-500">Snow:</span>{" "}
                    {forecastData.snowfall_sum[index]} cm
                  </div>
                </div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default DailyForecast;
