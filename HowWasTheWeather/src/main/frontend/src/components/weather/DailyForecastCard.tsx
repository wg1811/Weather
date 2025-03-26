import React, { useState } from "react";
import {
  DailyForecastEntry,
  HourlyForecastEntry,
  getWeatherDetails,
} from "../../types/weather";
import HourlyForecastCard from "./HourlyForecastCard";

interface DailyForecastProps {
  dailyData: DailyForecastEntry[];
  hourlyData: HourlyForecastEntry[];
}

const DailyForecastCard: React.FC<DailyForecastProps> = ({
  dailyData,
  hourlyData,
}) => {
  const [showExtendedForecast, setShowExtendedForecast] =
    useState<boolean>(false);
  const [selectedDay, setSelectedDay] = useState<string | null>(null);

  // Format date for display
  const formatDate = (dateString: string): string => {
    const date = new Date(dateString);
    return date.toLocaleDateString("en-US", {
      weekday: "short",
      month: "short",
      day: "numeric",
    });
  };

  // Calculate wind direction arrow rotation
  const getWindDirectionStyle = (degrees: number) => {
    return { transform: `rotate(${degrees}deg)` };
  };

  // Get visible forecast data based on toggle state
  const getVisibleDailyForecast = (): DailyForecastEntry[] => {
    return showExtendedForecast ? dailyData : dailyData.slice(0, 7);
  };

  // Filter hourly data based on selected day
  const getHourlyForecastForSelectedDate = (
    dayTime: string
  ): HourlyForecastEntry[] => {
    // if (!hourlyData || !Array.isArray(hourlyData)) {
    //   console.error("hourlyData is missing or not an array:", hourlyData);
    //   return [];
    // }
    // if (hourlyData.length === 0) {
    //   console.error("hourlyData is empty:", hourlyData);
    //   return [];
    // }
    const selectedDate = new Date(dayTime).toDateString();
    const filteredData = hourlyData.filter((hour) => {
      return new Date(hour.time).toDateString() === selectedDate;
    });
    // console.log(
    //   "Selected Date:",
    //   selectedDate,
    //   "Filtered Hourly Data:",
    //   filteredData
    // );
    return filteredData;
  };

  return (
    <div className="bg-white rounded-lg shadow-lg p-6">
      <div className="flex justify-between items-center mb-4">
        <h3 className="text-xl font-semibold">Daily Forecast</h3>
        <button
          onClick={() => setShowExtendedForecast(!showExtendedForecast)}
          className="text-blue-500 hover:text-blue-700 text-sm font-medium"
        >
          {showExtendedForecast ? "Show 7 Days" : "Show 16 Days"}
        </button>
      </div>

      <div className="space-y-4">
        {getVisibleDailyForecast().map((day, idx) => (
          <div key={idx}>
            <div
              onClick={() =>
                setSelectedDay(selectedDay === day.time ? null : day.time)
              }
              className={`flex items-center justify-between border-b last:border-b-0 p-4 cursor-pointer transition-colors duration-200 rounded-md ${
                selectedDay === day.time
                  ? "bg-blue-100 border-blue-200 shadow-sm"
                  : "bg-white " +
                    (idx === 0 ? "border-blue-100" : "border-gray-100")
              } hover:bg-blue-100`}
            >
              <div className="flex items-center">
                <div className="w-16 text-5xl leading-none mr-4">
                  {getWeatherDetails(day.weather_code).icon}
                </div>
                <div>
                  <p className={`${idx === 0 ? "font-semibold" : ""}`}>
                    {idx === 0 ? "Today" : formatDate(day.time)}
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
                    <span className="text-sm">
                      {day.precipitation_probability_max}%
                    </span>
                  </div>
                  <div className="flex items-center">
                    <span className="text-xs text-gray-500 mr-1">💨</span>
                    <span
                      className="inline-block mr-1"
                      style={getWindDirectionStyle(
                        day.wind_direction_10m_dominant
                      )}
                    >
                      ↑
                    </span>
                    <span className="text-sm">
                      {Math.round(day.wind_speed_10m_max)} km/h
                    </span>
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
            <div
              className={`mt-4 overflow-hidden transition-all duration-800 ease-in-out ${
                selectedDay === day.time
                  ? "max-h-96 opacity-100"
                  : "max-h-0 opacity-0"
              }`}
            >
              <HourlyForecastCard
                hourlyData={getHourlyForecastForSelectedDate(day.time)}
              />
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default DailyForecastCard;
