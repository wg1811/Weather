import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import LocationSearch from "../components/common/LocationSearch";
import CurrentWeatherCard from "../components/weather/CurrentWeatherCard";
import HourlyForecastCard from "../components/weather/HourlyForecastCard";
import DailyForecastCard from "../components/weather/DailyForecastCard";
import { weatherService, processDailyForecast, processHourlyForecast, processFullHourlyForecast } from "../services/weatherService";
import { authService } from "../services/authService";
import { ForecastDTO } from "../types/weather";

// Interface for location with potential coordinates
interface Location {
  name: string;
  latitude?: number;
  longitude?: number;
}

const ForecastPage: React.FC = () => {
  // Main state for weather forecast data
  const [forecastData, setForecastData] = useState<ForecastDTO | null>(null);
  
  // Location state - stores the selected location info
  const [location, setLocation] = useState<Location | null>(null);
  
  // UI states
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>("");
  
  const navigate = useNavigate();
  
  // Check authentication status when component mounts
  useEffect(() => {
    if (!authService.isValidAuthToken()) {
      navigate("/login");
    }
  }, [navigate]);
  
  // Handler for logout
  const handleLogout = () => {
    authService.logout();
    navigate("/login");
  };
  
  // Handler for location selection
  const handleLocationSelect = async (locationName: string) => {
    // Reset states
    setLoading(true);
    setError("");
    setForecastData(null);
    setLocation(null);

    try {
      // Fetch forecast data from your API
      const data = await weatherService.getForecast(locationName);
      
      // Update state with the fetched data
      setForecastData(data);
      console.log("Forecast Data on forecastpage:", data);
      setLocation({ 
        name: locationName,
        latitude: data.latitude,
        longitude: data.longitude
      });
    } catch (err) {
      console.error("Error fetching weather data:", err);
      setError("Failed to fetch weather data. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container mx-auto p-4 max-w-6xl">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold text-blue-500">Weather Forecast</h1>
        <button 
          onClick={handleLogout}
          className="bg-gray-100 hover:bg-gray-200 text-gray-800 font-semibold py-2 px-4 border border-gray-200 rounded shadow"
        >
          Logout
        </button>
      </div>

      {/* Location Search */}
      <div className="mb-8">
        <LocationSearch onLocationSelect={handleLocationSelect} />
      </div>

      {/* Loading State */}
      {loading && (
        <div className="text-center p-8">
          <div className="inline-block animate-spin rounded-full h-8 w-8 border-t-2 border-b-2 border-blue-500 mb-2"></div>
          <p className="text-gray-600">Loading weather data...</p>
        </div>
      )}

      {/* Error Message */}
      {error && (
        <div className="bg-red-100 border-l-4 border-red-500 text-red-700 p-4 mb-6" role="alert">
          <p>{error}</p>
        </div>
      )}

      {/* Weather Display */}
      {forecastData && location && (
        <div className="space-y-8">
          {/* Current Weather */}
          <CurrentWeatherCard forecastData={forecastData} locationName={location.name} />
          
          {/* Hourly Forecast */}
          <HourlyForecastCard hourlyData={processHourlyForecast(forecastData)} />
          
          {/* Daily Forecast */}
          <DailyForecastCard 
          dailyData={processDailyForecast(forecastData)} 
          hourlyData={processFullHourlyForecast(forecastData)} />
                  </div>
      )}
    </div>
  );
};

export default ForecastPage;