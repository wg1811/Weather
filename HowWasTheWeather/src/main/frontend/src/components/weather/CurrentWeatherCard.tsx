import React from "react";
import { ForecastDTO } from "../../types/weather";
import { getWeatherDetails } from "../../types/weather";

interface CurrentWeatherCardProps {
  forecastData: ForecastDTO;
  locationName: string;
  isFavorite: boolean;
  onToggleFavorite: () => void;
}

const CurrentWeatherCard: React.FC<CurrentWeatherCardProps> = ({
  forecastData,
  locationName,
  isFavorite,
  onToggleFavorite,
}) => {
  // Format date for display
  const formatDate = (dateString: string): string => {
    const date = new Date(dateString);
    return date.toLocaleDateString("en-US", {
      weekday: "short",
      month: "short",
      day: "numeric",
    });
  };

  // Format time for display
  const formatTime = (timeString: string): string => {
    const date = new Date(timeString);
    return date.toLocaleTimeString("en-US", {
      hour: "numeric",
      minute: "2-digit",
      hour12: true,
    });
  };

  // Calculate wind direction arrow rotation
  const getWindDirectionStyle = (degrees: number) => {
    return { transform: `rotate(${degrees}deg)` };
  };

  return (
    <div className="bg-white rounded-lg shadow-lg overflow-hidden">
      <div className="bg-gradient-to-r from-blue-500 to-blue-600 text-white p-6">
        <div className="flex flex-col md:flex-row md:justify-between md:items-center">
          <div>
          <div className="flex items-center">
          <h2 className="text-2xl font-bold">{locationName}</h2>
          {onToggleFavorite && (
            <button 
              onClick={onToggleFavorite} 
              className="text-2xl ml-2"
              aria-label={isFavorite ? "Remove from favorites" : "Add to favorites"}
            >
              {isFavorite ? "❤️" : "🤍"}
            </button>
          )}
        </div>
        <p className="text-sm opacity-90">
          {formatDate(forecastData.currentDTO.time)} • Updated{" "}
          {formatTime(forecastData.currentDTO.time)}
        </p>
      </div>
          <div className="flex items-center mt-4 md:mt-0">
            <span className="text-7xl mr-4">
              {getWeatherDetails(forecastData.currentDTO.weather_code).icon}
            </span>
            <div>
              <span className="text-4xl font-bold">
                {Math.round(forecastData.currentDTO.temperature_2m)}°
              </span>
              <p className="text-lg">
                {
                  getWeatherDetails(forecastData.currentDTO.weather_code)
                    .description
                }
              </p>
            </div>
          </div>
        </div>
      </div>

      <div className="grid grid-cols-2 md:grid-cols-4 gap-4 p-4">
        <div className="text-center p-2">
          <p className="text-gray-500 text-sm">Feels Like</p>
          <p className="text-lg font-semibold">
            {Math.round(forecastData.currentDTO.apparent_temperature)}°
          </p>
        </div>
        <div className="text-center p-2">
          <p className="text-gray-500 text-sm">Humidity</p>
          <p className="text-lg font-semibold">
            {forecastData.currentDTO.relative_humidity_2m}%
          </p>
        </div>
        <div className="text-center p-2">
          <p className="text-gray-500 text-sm">Wind</p>
          <div className="flex items-center justify-center">
            <span
              className="inline-block mr-1"
              style={getWindDirectionStyle(
                forecastData.currentDTO.wind_direction_10m
              )}
            >
              ↑
            </span>
            <p className="text-lg font-semibold">
              {Math.round(forecastData.currentDTO.wind_speed_10m)} km/h
            </p>
          </div>
        </div>
        <div className="text-center p-2">
          <p className="text-gray-500 text-sm">Precipitation</p>
          <p className="text-lg font-semibold">
            {forecastData.currentDTO.precipitation} mm
          </p>
        </div>
      </div>
    </div>
  );
};

export default CurrentWeatherCard;
