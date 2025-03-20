//  This is claude's code for the whole page.  needs to be broken into components.

// import React, { useState } from "react";
// import { forecastApi } from "../api/forecastApi";
// import { 
//   ForecastDTO, 
//   DailyForecastEntry, 
//   HourlyForecastEntry,
//   getWeatherDetails 
// } from "../types/weather";
// import LocationSearch from "../components/common/LocationSearch";
// import CurrentWeather from "../components/weather/CurrentWeather";

// // Interface for location with potential coordinates
// interface Location {
//   name: string;
//   latitude?: number;
//   longitude?: number;
// }

// const ForecastPage: React.FC = () => {
//   // Main state for weather forecast data
//   const [forecastData, setForecastData] = useState<ForecastDTO | null>(null);
  
//   // Location state - stores the selected location info
//   const [location, setLocation] = useState<Location | null>(null);
  
//   // UI states
//   const [loading, setLoading] = useState<boolean>(false);
//   const [error, setError] = useState<string>("");
//   const [showExtendedForecast, setShowExtendedForecast] = useState<boolean>(false);
  
//   // Function to process daily forecast data and limit to visible days
//   const getVisibleDailyForecast = (): DailyForecastEntry[] => {
//     if (!forecastData?.dailyDTO) return [];
    
//     // Convert array-based DTO to array of objects for easier use
//     const dailyEntries: DailyForecastEntry[] = forecastData.dailyDTO.time.map((time, index) => ({
//       time,
//       weather_code: forecastData.dailyDTO.weather_code[index],
//       temperature_2m_max: forecastData.dailyDTO.temperature_2m_max[index],
//       temperature_2m_min: forecastData.dailyDTO.temperature_2m_min[index],
//       apparent_temperature_max: forecastData.dailyDTO.apparent_temperature_max[index],
//       apparent_temperature_min: forecastData.dailyDTO.apparent_temperature_min[index],
//       sunrise: forecastData.dailyDTO.sunrise[index],
//       sunset: forecastData.dailyDTO.sunset[index],
//       precipitation_sum: forecastData.dailyDTO.precipitation_sum[index],
//       rain_sum: forecastData.dailyDTO.rain_sum[index],
//       showers_sum: forecastData.dailyDTO.showers_sum[index],
//       snowfall_sum: forecastData.dailyDTO.snowfall_sum[index],
//       precipitation_probability_max: forecastData.dailyDTO.precipitation_probability_max[index],
//       wind_speed_10m_max: forecastData.dailyDTO.wind_speed_10m_max[index],
//       wind_gusts_10m_max: forecastData.dailyDTO.wind_gusts_10m_max[index],
//       wind_direction_10m_dominant: forecastData.dailyDTO.wind_direction_10m_dominant[index]
//     }));
    
//     // Return either 7 days or full 16 days based on showExtendedForecast state
//     return showExtendedForecast ? dailyEntries : dailyEntries.slice(0, 7);
//   };
  
//   // Function to process hourly forecast data for the next 24 hours
//   const getNext24HoursForecast = (): HourlyForecastEntry[] => {
//     if (!forecastData?.hourlyDTO) return [];
    
//     // Take the first 24 entries (24 hours) from the hourly forecast
//     const hourlyEntries: HourlyForecastEntry[] = forecastData.hourlyDTO.time.slice(0, 24).map((time, index) => ({
//       time,
//       temperature_2m: forecastData.hourlyDTO.temperature_2m[index],
//       apparent_temperature: forecastData.hourlyDTO.apparent_temperature[index],
//       precipitation_probability: forecastData.hourlyDTO.precipitation_probability[index],
//       precipitation: forecastData.hourlyDTO.precipitation[index],
//       rain: forecastData.hourlyDTO.rain[index],
//       showers: forecastData.hourlyDTO.showers[index],
//       snowfall: forecastData.hourlyDTO.snowfall[index],
//       weather_code: forecastData.hourlyDTO.weather_code[index],
//       cloud_cover: forecastData.hourlyDTO.cloud_cover[index],
//       wind_speed_10m: forecastData.hourlyDTO.wind_speed_10m[index],
//       wind_direction_10m: forecastData.hourlyDTO.wind_direction_10m[index]
//     }));
    
//     return hourlyEntries;
//   };

//   // Handler for location selection
//   const handleLocationSelect = async (locationName: string) => {
//     // Reset states
//     setLoading(true);
//     setError("");
//     setForecastData(null);
//     setLocation(null);

//     try {
//       // Fetch forecast data from your API
//       const data = await forecastApi.getForecast(locationName);
      
//       // Update state with the fetched data
//       setForecastData(data);
//       setLocation({ 
//         name: locationName,
//         latitude: data.latitude,
//         longitude: data.longitude
//       });
//     } catch (err) {
//       console.error("Error fetching weather data:", err);
//       setError("Failed to fetch weather data. Please try again.");
//     } finally {
//       setLoading(false);
//     }
//   };

//   // Format date for display
//   const formatDate = (dateString: string): string => {
//     const date = new Date(dateString);
//     return date.toLocaleDateString('en-US', { 
//       weekday: 'short', 
//       month: 'short', 
//       day: 'numeric' 
//     });
//   };

//   // Format time for display
//   const formatTime = (timeString: string): string => {
//     const date = new Date(timeString);
//     return date.toLocaleTimeString('en-US', { 
//       hour: 'numeric', 
//       minute: '2-digit', 
//       hour12: true 
//     });
//   };

//   // Calculate wind direction arrow rotation
//   const getWindDirectionStyle = (degrees: number) => {
//     return { transform: `rotate(${degrees}deg)` };
//   };

//   return (
//     <div className="container mx-auto p-4 max-w-6xl">
//       <h1 className="text-3xl font-bold mb-6 text-center">Weather Forecast</h1>

//       {/* Location Search */}
//       <div className="mb-8">
//         <LocationSearch onLocationSelect={handleLocationSelect} />
//       </div>

//       {/* Loading State */}
//       {loading && (
//         <div className="text-center p-8">
//           <div className="inline-block animate-spin rounded-full h-8 w-8 border-t-2 border-b-2 border-blue-500 mb-2"></div>
//           <p className="text-gray-600">Loading weather data...</p>
//         </div>
//       )}

//       {/* Error Message */}
//       {error && (
//         <div className="bg-red-100 border-l-4 border-red-500 text-red-700 p-4 mb-6" role="alert">
//           <p>{error}</p>
//         </div>
//       )}

//       {/* Weather Display */}
//       {forecastData && location && (
//         <div className="space-y-8">
//           {/* Current Weather Card */}
//           <div className="bg-white rounded-lg shadow-lg overflow-hidden">
//             <div className="bg-gradient-to-r from-blue-500 to-blue-600 text-white p-6">
//               <div className="flex flex-col md:flex-row md:justify-between md:items-center">
//                 <div>
//                   <h2 className="text-2xl font-bold">{location.name}</h2>
//                   <p className="text-sm opacity-90">
//                     {formatDate(forecastData.currentDTO.time)} • 
//                     Updated {formatTime(forecastData.currentDTO.time)}
//                   </p>
//                 </div>
//                 <div className="flex items-center mt-4 md:mt-0">
//                   <span className="text-5xl mr-4">
//                     {getWeatherDetails(forecastData.currentDTO.weather_code).icon}
//                   </span>
//                   <div>
//                     <span className="text-4xl font-bold">
//                       {Math.round(forecastData.currentDTO.temperature_2m)}°
//                     </span>
//                     <p className="text-lg">
//                       {getWeatherDetails(forecastData.currentDTO.weather_code).description}
//                     </p>
//                   </div>
//                 </div>
//               </div>
//             </div>
            
//             <div className="grid grid-cols-2 md:grid-cols-4 gap-4 p-4">
//               <div className="text-center p-2">
//                 <p className="text-gray-500 text-sm">Feels Like</p>
//                 <p className="text-lg font-semibold">
//                   {Math.round(forecastData.currentDTO.apparent_temperature)}°
//                 </p>
//               </div>
//               <div className="text-center p-2">
//                 <p className="text-gray-500 text-sm">Humidity</p>
//                 <p className="text-lg font-semibold">
//                   {forecastData.currentDTO.relative_humidity_2m}%
//                 </p>
//               </div>
//               <div className="text-center p-2">
//                 <p className="text-gray-500 text-sm">Wind</p>
//                 <div className="flex items-center justify-center">
//                   <span className="inline-block mr-1" style={getWindDirectionStyle(forecastData.currentDTO.wind_direction_10m)}>
//                     ↑
//                   </span>
//                   <p className="text-lg font-semibold">
//                     {Math.round(forecastData.currentDTO.wind_speed_10m)} km/h
//                   </p>
//                 </div>
//               </div>
//               <div className="text-center p-2">
//                 <p className="text-gray-500 text-sm">Precipitation</p>
//                 <p className="text-lg font-semibold">
//                   {forecastData.currentDTO.precipitation} mm
//                 </p>
//               </div>
//             </div>
//           </div>

//           {/* Hourly Forecast */}
//           <div className="bg-white rounded-lg shadow-lg p-6">
//             <h3 className="text-xl font-semibold mb-4">Hourly Forecast</h3>
//             <div className="overflow-x-auto">
//               <div className="flex space-x-6 pb-4 min-w-max">
//                 {getNext24HoursForecast().map((hour, idx) => (
//                   <div key={idx} className="flex flex-col items-center text-center min-w-[80px]">
//                     <p className="text-gray-500 text-sm">
//                       {idx === 0 ? 'Now' : formatTime(hour.time)}
//                     </p>
//                     <div className="my-2 text-xl">
//                       {getWeatherDetails(hour.weather_code).icon}
//                     </div>
//                     <p className="font-semibold">
//                       {Math.round(hour.temperature_2m)}°
//                     </p>
//                     <p className="text-xs text-gray-500 mt-1">
//                       {hour.precipitation_probability}%
//                     </p>
//                   </div>
//                 ))}
//               </div>
//             </div>
//           </div>

//           {/* Daily Forecast */}
//           <div className="bg-white rounded-lg shadow-lg p-6">
//             <div className="flex justify-between items-center mb-4">
//               <h3 className="text-xl font-semibold">Daily Forecast</h3>
//               <button 
//                 onClick={() => setShowExtendedForecast(!showExtendedForecast)}
//                 className="text-blue-500 hover:text-blue-700 text-sm font-medium"
//               >
//                 {showExtendedForecast ? 'Show 7 Days' : 'Show 16 Days'}
//               </button>
//             </div>
            
//             <div className="space-y-4">
//               {getVisibleDailyForecast().map((day, idx) => (
//                 <div 
//                   key={idx} 
//                   className={`flex items-center justify-between border-b last:border-b-0 pb-3 ${
//                     idx === 0 ? 'border-blue-100' : 'border-gray-100'
//                   }`}
//                 >
//                   <div className="flex items-center">
//                     <div className="w-10 text-lg mr-4">
//                       {getWeatherDetails(day.weather_code).icon}
//                     </div>
//                     <div>
//                       <p className={`${idx === 0 ? 'font-semibold' : ''}`}>
//                         {idx === 0 ? 'Today' : formatDate(day.time)}
//                       </p>
//                       <p className="text-xs text-gray-500">
//                         {getWeatherDetails(day.weather_code).description}
//                       </p>
//                     </div>
//                   </div>
//                   <div className="flex items-center">
//                     <div className="flex flex-col items-end mr-6">
//                       <div className="flex items-center">
//                         <span className="text-xs text-blue-500 mr-1">💧</span>
//                         <span className="text-sm">{day.precipitation_probability_max}%</span>
//                       </div>
//                       <div className="flex items-center">
//                         <span className="text-xs text-gray-500 mr-1">💨</span>
//                         <span className="text-sm">{Math.round(day.wind_speed_10m_max)} km/h</span>
//                       </div>
//                     </div>
//                     <div className="text-right min-w-[80px]">
//                       <p className="font-medium">
//                         {Math.round(day.temperature_2m_max)}° 
//                         <span className="text-gray-400 ml-1">
//                           {Math.round(day.temperature_2m_min)}°
//                         </span>
//                       </p>
//                     </div>
//                   </div>
//                 </div>
//               ))}
//             </div>
//           </div>
//         </div>
//       )}
//     </div>
//   );
// };

// export default ForecastPage;