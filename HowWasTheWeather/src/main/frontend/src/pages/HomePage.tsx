// src/main/frontend/src/pages/HomePage.jsx
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import LocationSearch from "../components/common/LocationSearch";
import CurrentWeather from "../components/weather/CurrentWeather";
import { forecastApi } from "../api/forecastApi";
//import { useAuth } from "../contexts/AuthContext";

const HomePage = () => {
  const navigate = useNavigate();
  const { isAuthenticated } = useAuth();
  const [currentLocation, setCurrentLocation] = useState(null);
  const [weatherData, setWeatherData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [favorites, setFavorites] = useState([]);

  // Fetch user's favorite locations if authenticated
  useEffect(() => {
    if (isAuthenticated) {
      fetchFavoriteLocations();
    }
  }, [isAuthenticated]);

  // Try to get user's current location on page load
  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const { latitude, longitude } = position.coords;
          setCurrentLocation({ lat: latitude, lon: longitude });
          fetchWeatherData(latitude, longitude);
        },
        (error) => {
          console.error("Error getting current location:", error);
          // Default to a fallback location (e.g., New York)
          const fallbackLat = 40.7128;
          const fallbackLon = -74.006;
          setCurrentLocation({ lat: fallbackLat, lon: fallbackLon });
          fetchWeatherData(fallbackLat, fallbackLon);
        }
      );
    }
  }, []);

  const fetchFavoriteLocations = async () => {
    try {
      const data = await weatherApi.getFavoriteLocations();
      setFavorites(data);
    } catch (err) {
      console.error("Error fetching favorite locations:", err);
    }
  };

  const fetchWeatherData = async (lat, lon) => {
    setLoading(true);
    setError(null);
    try {
      // Fetch both current weather and daily forecast
      const [currentData, dailyData] = await Promise.all([
        weatherApi.getCurrentWeather(lat, lon),
        weatherApi.getDailyForecast(lat, lon),
      ]);

      setWeatherData({
        current: currentData,
        daily: dailyData,
        location: { lat, lon },
      });
    } catch (err) {
      setError("Failed to fetch weather data. Please try again.");
      console.error("Error fetching weather data:", err);
    } finally {
      setLoading(false);
    }
  };

  const handleLocationSelect = (location) => {
    const { lat, lon, name } = location;
    setCurrentLocation({ lat, lon, name });
    fetchWeatherData(lat, lon);
  };

  const handleViewForecast = () => {
    if (currentLocation) {
      navigate(`/forecast/${currentLocation.lat}/${currentLocation.lon}`);
    }
  };

  const handleSaveLocation = async () => {
    if (!isAuthenticated) {
      navigate("/login");
      return;
    }

    if (currentLocation) {
      try {
        await weatherApi.addFavoriteLocation({
          latitude: currentLocation.lat,
          longitude: currentLocation.lon,
          name: currentLocation.name || "Unnamed Location",
        });
        fetchFavoriteLocations();
      } catch (err) {
        console.error("Error saving location:", err);
      }
    }
  };

  return (
    <div className="max-w-4xl mx-auto">
      <h1 className="text-3xl font-bold mb-6">Weather Forecast</h1>

      <div className="mb-6">
        <LocationSearch onLocationSelect={handleLocationSelect} />
      </div>

      {loading && (
        <div className="text-center py-12">
          <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-blue-500 mx-auto"></div>
          <p className="mt-4">Loading weather data...</p>
        </div>
      )}

      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
          {error}
        </div>
      )}

      {!loading && !error && weatherData && (
        <div>
          <CurrentWeather
            currentWeather={weatherData.current}
            location={weatherData.location}
          />

          <div className="flex justify-between mt-6">
            <button
              onClick={handleViewForecast}
              className="bg-blue-500 hover:bg-blue-600 text-white font-medium py-2 px-4 rounded transition-colors"
            >
              View Full Forecast
            </button>

            {isAuthenticated && (
              <button
                onClick={handleSaveLocation}
                className="bg-green-500 hover:bg-green-600 text-white font-medium py-2 px-4 rounded transition-colors"
              >
                Save to Favorites
              </button>
            )}
          </div>

          {isAuthenticated && favorites.length > 0 && (
            <div className="mt-8">
              <h2 className="text-xl font-semibold mb-3">
                Your Favorite Locations
              </h2>
              <div className="grid grid-cols-2 md:grid-cols-3 gap-3">
                {favorites.map((location) => (
                  <div
                    key={location.id}
                    onClick={() =>
                      handleLocationSelect({
                        lat: location.latitude,
                        lon: location.longitude,
                        name: location.name,
                      })
                    }
                    className="bg-white shadow rounded p-3 cursor-pointer hover:shadow-md transition-shadow"
                  >
                    <div className="font-medium">{location.name}</div>
                    <div className="text-sm text-gray-500">
                      {location.latitude.toFixed(2)},{" "}
                      {location.longitude.toFixed(2)}
                    </div>
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default HomePage;
