import React, { useState } from "react";
import { DailyForecastEntry, getWeatherDetails } from "../../types/weather";

interface DailyForecastProps {
  dailyData: DailyForecastEntry[];
}

const DailyForecast: React.FC<DailyForecastProps> = ({ dailyData }) => {
  const [showExtendedForecast, setShowExtendedForecast] = useState<boolean>(false);
  
  // Format date for display
  const formatDate = (dateString: string): string => {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', { 
      weekday: 'short', 
      month: 'short', 
      day: 'numeric' 
    });
  };
  
  // Get visible forecast data based on toggle state
  const getVisibleDailyForecast = (): DailyForecastEntry[] => {
    return showExtendedForecast ? dailyData : dailyData.slice(0, 7);
  };

  return (
    <div className="bg-white rounded-lg shadow-lg p-6">
      <div className="flex justify-between items-center mb-4">
        <h3 className="text-xl font-semibold">Daily Forecast</h3>
        <button 
          onClick={() => setShowExtendedForecast(!showExtendedForecast)}
          className="text-blue-500 hover:text-blue-700 text-sm font-medium"
        >
          {showExtendedForecast ? 'Show 7 Days' : 'Show 16 Days'}
        </button>
      </div>
      
      <div className="space-y-4">
        {getVisibleDailyForecast().map((day, idx) => (
          <div 
            key={idx} 
            className={`flex items-center justify-between border-b last:border-b-0 pb-3 ${
              idx === 0 ? 'border-blue-100' : 'border-gray-100'
            }`}
          >
            <div className="flex items-center">
              <div className="w-10 text-lg mr-4">
                {getWeatherDetails(day.weather_code).icon}
              </div>
              <div>
                <p className={`${idx === 0 ? 'font-semibold' : ''}`}>
                  {idx === 0 ? 'Today' : formatDate(day.time)}
                </p>
                <p className="text-xs text-gray-500">
                  {getWeatherDetails(day.weather_code).description}
                </p>
              </div>
            </div>
            <div className="flex items-center">
              <div className="flex flex-col items-end mr-6">
                <div className="flex items-center">
                  <span className="text-xs text-blue-500 mr-1">💧</span>
                  <span className="text-sm">{day.precipitation_probability_max}%</span>
                </div>
                <div className="flex items-center">
                  <span className="text-xs text-gray-500 mr-1">💨</span>
                  <span className="text-sm">{Math.round(day.wind_speed_10m_max)} km/h</span>  {/* should this be 2m? */}
                </div>
              </div>
              <div className="text-right min-w-[80px]">
                <p className="font-medium">
                  {Math.round(day.temperature_2m_max)}° 
                  <span className="text-gray-400 ml-1">
                    {Math.round(day.temperature_2m_min)}°
                  </span>
                </p>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default DailyForecast;