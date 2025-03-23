import { CurrentWeatherDTO } from '../../types/weather';

// Get weather icon and description based on code.  Need to abstract this as it repeats in multiple components.
type WeatherMapping = {
  [key: number]: { description: string; icon: string };
};
//  Need to abstract this as it repeats in multiple components.
const getWeatherInfo = (code: number): { description: string; icon: string } => {
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
    99: { description: 'Thunderstorm with heavy hail', icon: '⛈️' },
  };

  return weatherMapping[code] || { description: 'Unknown', icon: '❓' };
};

// Format wind direction
const getWindDirection = (degrees: number) => {
  const directions = ['N', 'NE', 'E', 'SE', 'S', 'SW', 'W', 'NW'];
  const index = Math.round(degrees / 45) % 8;
  return directions[index];
};

// Define the props for the CurrentWeather component
interface CurrentWeatherProps {
  currentWeatherDTO: CurrentWeatherDTO;
  location: { name: string };
}

// CurrentWeather component
const CurrentWeather = ({ currentWeatherDTO, location }: CurrentWeatherProps) => {
  console.log(currentWeatherDTO, " is the current weather DTO");
  console.log(location, " is the location");
  
  // Check is currentWeatherDTO exists
  if (!currentWeatherDTO) {
    return <div>Loading current weather...</div>;
  }

  const {
    time,
    temperature_2m,
    relative_humidity_2m,
    apparent_temperature,
    is_day,
    precipitation,
    rain,
    showers,
    snowfall,
    weather_code,
    cloud_cover,
    wind_speed_10m,
    wind_direction_10m,
    wind_gusts_10m
  } = currentWeatherDTO;

  const weatherInfo = getWeatherInfo(weather_code);

  return (
    <div className="bg-white rounded-lg shadow-md p-6">
      <div className="flex justify-between items-start">
        <div>
          <h2 className="text-2xl font-bold mb-1">Current Weather</h2>
          <p className="text-gray-500 mb-4">
            Time: {time}
            {is_day ? ' ☀️' : ' 🌙'}
          </p>
          <p className="text-gray-700 mb-2">
            Location: {location.name}
          </p>
        </div>
        <div className="text-6xl">{weatherInfo.icon}</div>
      </div>
  
      <div className="mt-4 grid grid-cols-1 md:grid-cols-3 gap-6">
        {/* Temperature Section */}
        <div className="space-y-4">
          <div className="flex items-end">
            <span className="text-5xl font-bold">{temperature_2m}°C</span>
            <span className="ml-2 text-gray-500">
              Feels like {apparent_temperature}°C
            </span>
          </div>
          <p className="text-xl">{weatherInfo.description}</p>
          <div className="flex items-center gap-2 text-gray-500">
            <span>☁️ Cloud Cover</span>
            <span className="font-semibold">{cloud_cover}%</span>
          </div>
        </div>
  
        {/* Precipitation Section */}
        <div className="space-y-4">
          <h3 className="text-lg font-semibold text-gray-800 mb-2">Precipitation</h3>
          <div className="grid grid-cols-2 gap-4">
            {precipitation > 0 && (
              <div>
                <p className="text-gray-500">Total Precipitation</p>
                <p className="font-semibold">{precipitation.toFixed(1)} mm</p>
              </div>
            )}
            {rain > 0 && (
              <div>
                <p className="text-gray-500">Rain</p>
                <p className="font-semibold">{rain.toFixed(1)} mm</p>
              </div>
            )}
            {showers > 0 && (
              <div>
                <p className="text-gray-500">Showers</p>
                <p className="font-semibold">{showers.toFixed(1)} mm</p>
              </div>
            )}
            {snowfall > 0 && (
              <div>
                <p className="text-gray-500">Snowfall</p>
                <p className="font-semibold">{snowfall.toFixed(1)} cm</p>
              </div>
            )}
            {(precipitation === 0 && rain === 0 && showers === 0 && snowfall === 0) && (
              <div className="col-span-2 text-center text-gray-400">
                No precipitation
              </div>
            )}
          </div>
        </div>
  
        {/* Wind & Atmosphere Section */}
        <div className="space-y-4">
          <div>
            <p className="text-gray-500">Wind Speed</p>
            <p className="font-semibold">
              {wind_speed_10m} m/s ({wind_gusts_10m} m/s gusts)<br/>
              {getWindDirection(wind_direction_10m)}
            </p>
          </div>
          <div>
            <p className="text-gray-500">Humidity</p>
            <p className="font-semibold">{relative_humidity_2m}%</p>
          </div>
        </div>
      </div>
    </div>
  );
  
};

export default CurrentWeather;