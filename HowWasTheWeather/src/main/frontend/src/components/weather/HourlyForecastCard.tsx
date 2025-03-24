import React from "react";
import { HourlyForecastEntry, getWeatherDetails } from "../../types/weather";

interface HourlyForecastProps {
  hourlyData: HourlyForecastEntry[];
}

const HourlyForecastCard: React.FC<HourlyForecastProps> = ({ hourlyData }) => {
  // Format time for display
  const formatTime = (timeString: string): string => {
    const date = new Date(timeString);
    return date.toLocaleTimeString('en-US', { 
      hour: 'numeric', 
      minute: '2-digit', 
      hour12: true 
    });
  };

  return (
    <div className="bg-white rounded-lg shadow-lg p-6">
      <h3 className="text-xl font-semibold mb-4">Hourly Forecast</h3>
      <div className="overflow-x-auto">
      <div className="flex space-x-6 pb-4 min-w-max">
  {hourlyData.length > 0 ? (
    hourlyData.map((hour, idx) => (
      <div key={idx} className="flex flex-col items-center text-center min-w-[80px]">
        <p className="text-gray-500 text-sm">
          {idx === 0 ? "Now" : formatTime(hour.time)}
        </p>
        <div className="my-2 text-5xl">
          {getWeatherDetails(hour.weather_code).icon}
        </div>
        <p className="font-semibold">
          {Math.round(hour.temperature_2m)}°
        </p>
        <p className="text-xs text-gray-500 mt-1">
          {hour.precipitation_probability}%
        </p>
      </div>
    ))
  ) : (
    <p>No hourly data available for this day.</p>
  )}
</div>
      </div>
    </div>
  );
};

export default HourlyForecastCard;