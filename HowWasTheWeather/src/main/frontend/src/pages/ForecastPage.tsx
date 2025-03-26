import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import LocationSearch from "../components/common/LocationSearch";
import CurrentWeatherCard from "../components/weather/CurrentWeatherCard";
import HourlyForecastCard from "../components/weather/HourlyForecastCard";
import DailyForecastCard from "../components/weather/DailyForecastCard";
import {
  weatherService,
  processDailyForecast,
  processHourlyForecast,
  processFullHourlyForecast,
} from "../services/weatherService";
import { favoriteService } from "../services/favoriteService";
import { authService } from "../services/authService";
import { ForecastDTO } from "../types/weather";

// Interface for location with potential coordinates
interface Location {
  name: string;
  latitude: number;
  longitude: number;
}

interface Favorite {
  id: string;
  name: string;
  latitude: number;
  longitude: number;
}

const ForecastPage: React.FC = () => {
  // Main state for weather forecast data
  const [forecastData, setForecastData] = useState<ForecastDTO | null>(null);

  // Location state - stores the selected location info
  const [location, setLocation] = useState<Location | null>(null);

  // UI states
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>("");

  // Fovorites state
  const [favorites, setFavorites] = useState<Favorite[]>([]);
  const [currentFavoriteIndex, setCurrentFavoriteIndex] = useState<number>(-1); // -1 for non-favorite

  const navigate = useNavigate();

  // Check authentication status and favorites when component mounts
  useEffect(() => {
    const fetchFavorites = async () => {
      try {
        const data = await favoriteService.getFavorites(); // Need to make this
        console.log("Favorites Data:", data);
        setFavorites(data);
        if (data.length > 0) {
          handleFavoriteSelect(0); // Load first favorite by default
        }
      } catch (err) {
        console.error("Error fetching favorites:", err); // If empty, will this error? just want to do nothing.
      }
    };
    if (authService.isValidAuthToken()) {
      fetchFavorites();
    } else {
      navigate("/login");
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [navigate]);

  const handleFavoriteSelect = async (index: number) => {
    const favorite = favorites[index];
    console.log("Selected favorite:", favorite, "Index:", index);
    setCurrentFavoriteIndex(index);
    setLoading(true);
    try {
      const data = await weatherService.getForecast(favorite.name);
      setForecastData(data);
      setLocation({
        name: favorite.name,
        latitude: favorite.latitude,
        longitude: favorite.longitude,
      });
    } catch (err) {
      console.log("Error fetching favorite location:", err);
      setError("Failed to load favorite location.");
    } finally {
      setLoading(false);
    }
  };

  const handleAddFavorite = async () => {
    if (!location || favorites.some((f) => f.name === location.name)) return;
    try {
      const newFavorite = await favoriteService.addFavorite(location); // Not sure if this is location.name or the whole object.
      setFavorites([...favorites, newFavorite]);
      setCurrentFavoriteIndex(favorites.length); // Switch to new favorite
    } catch (err) {
      console.error("Error adding favorite:", err);
      setError("Failed to add favorite.");
    }
  };

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
        longitude: data.longitude,
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
      {/* Header */}
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

      {/* Favorites Navigation */}
      {favorites.length > 0 && (
        <div className="flex space-x-4 mb-4 overflow-x-auto">
          {favorites.map((fav, index) => (
            <button
              key={fav.id}
              onClick={() => handleFavoriteSelect(index)}
              className={`px-4 py-2 rounded ${
                index === currentFavoriteIndex
                  ? "bg-blue-500 text-white"
                  : "bg-gray-200 text-gray-800"
              }`}
            >
              {fav.name}
            </button>
          ))}
        </div>
      )}

      {/* Loading State */}
      {loading && (
        <div className="text-center p-8">
          <div className="inline-block animate-spin rounded-full h-8 w-8 border-t-2 border-b-2 border-blue-500 mb-2"></div>
          <p className="text-gray-600">Loading weather data...</p>
        </div>
      )}

      {/* Error Message */}
      {error && (
        <div
          className="bg-red-100 border-l-4 border-red-500 text-red-700 p-4 mb-6"
          role="alert"
        >
          <p>{error}</p>
        </div>
      )}

      {/* Weather Display */}
      {forecastData && location && (
        <div className="space-y-8">
          {/* Current Weather */}
          <CurrentWeatherCard
            forecastData={forecastData}
            locationName={location.name}
            onAddFavorite={handleAddFavorite}
          />

          {/* Hourly Forecast */}
          <HourlyForecastCard
            hourlyData={processHourlyForecast(forecastData)}
          />

          {/* Daily Forecast */}
          <DailyForecastCard
            dailyData={processDailyForecast(forecastData)}
            hourlyData={processFullHourlyForecast(forecastData)}
          />
        </div>
      )}
    </div>
  );
};

export default ForecastPage;
