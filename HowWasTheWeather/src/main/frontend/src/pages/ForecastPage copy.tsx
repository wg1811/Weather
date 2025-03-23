// import { useState } from "react";
// import { forecastApi } from "../api/forecastApi"; 
// import { CurrentWeatherDTO } from "../types/weather"; 
// import LocationSearch from "../components/common/LocationSearch";
// import CurrentWeather from "../components/weather/CurrentWeather(OLD)";


// const ForecastPage = () => {
//   const [currentWeather, setCurrentWeather] =
//     useState<CurrentWeatherDTO | null>(null);
//   const [location, setLocation] = useState<{ name: string } | null>(null);
//   const [loading, setLoading] = useState(false);
//   const [error, setError] = useState("");

//   const handleLocationSelect = async (locationName: string) => {
//     setLoading(true);
//     setError("");
//     setCurrentWeather(null);
//     setLocation(null);

//     try {
//       // Fetch current weather data from your API
//       const weatherData = await forecastApi.getCurrentWeather(locationName) as CurrentWeatherDTO;

//       // Update state with the fetched data
//       setCurrentWeather(weatherData);
//       setLocation({ name: locationName });
//     } catch (err) {
//       console.error("Error fetching weather data:", err);
//       setError("Failed to fetch weather data. Please try again.");
//     } finally {
//       setLoading(false);
//     }
//   };

//   return (
//     <div className="container mx-auto p-6">
//       <h1 className="text-3xl font-bold mb-6 text-center">Weather Forecast</h1>

//       {/* Location Search */}
//       <LocationSearch onLocationSelect={handleLocationSelect} />

//       {/* Loading State */}
//       {loading && <div className="text-center text-gray-500">Loading...</div>}

//       {/* Error Message */}
//       {error && <div className="text-center text-red-500 mt-4">{error}</div>}

//       {/* Current Weather Display */}
//       {currentWeather && location && (
//         <div className="mt-6">
//           <CurrentWeather
//             currentWeatherDTO={currentWeather}
//             location={location}
//           />
//         </div>
//       )}
//     </div>
//   );
// };

// export default ForecastPage;
