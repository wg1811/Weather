import { useState } from 'react';

interface LocationSearchProps {
  onLocationSelect: (location: string) => void;
}

const LocationSearch = ({ onLocationSelect }: LocationSearchProps) => {
  const [locationInput, setLocationInput] = useState('');
  const [error, setError] = useState('');

  const handleLocationSearch = async (e: React.FormEvent) => {
    e.preventDefault();
    
    try {
      if (!locationInput.trim()) {
        setError('Please enter a location');
        return;
      }
      
      setError('');
      // Pass the location back to the parent component
      onLocationSelect(locationInput);
      
    } catch (error) {
      console.error('Error searching for location:', error);
      setError('Error searching for location. Please try again.');
    }
  };

  return (
    <div className="mb-4">
      <form onSubmit={handleLocationSearch} className="flex flex-col sm:flex-row gap-2">
        <input
          type="text"
          value={locationInput}
          onChange={(e) => setLocationInput(e.target.value)}
          placeholder="Enter location name..."
          className="flex-1 px-4 py-2 border rounded"
        />
        <button
          type="submit"
          className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
        >
          Get Forecast
        </button>
      </form>
      {error && <div className="text-red-500 mt-2">{error}</div>}
      <div className="text-sm text-gray-300 mt-1">
        Enter a city name to get the current weather and forecast.
      </div>
    </div>
  );
};

export default LocationSearch;