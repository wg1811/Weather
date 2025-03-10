import axios from "axios";

const API_BASE_URL = "/api/forecast";

export const forecastApi = {
  getCurrentWeather: async (location: string) => {
    try {
      const response = await axios.get(`${API_BASE_URL}/current`, {
        params: { location },
      });
      return response.data;
    } catch (error) {
      console.error("Error fetching current weather:", error);
      throw error;
    }
  },

  getHourlyForecast: async (location: string) => {
    try {
      const response = await axios.get(`${API_BASE_URL}/hourly`, {
        params: { location },
      });
      return response.data;
    } catch (error) {
      console.error("Error fetching hourly forecast:", error);
      throw error;
    }
  },

  getDailyForecast: async (location: string) => {
    try {
      const response = await axios.get(`${API_BASE_URL}/daily`, {
        params: { location },
      });
      return response.data;
    } catch (error) {
      console.error("Error fetching daily forecast:", error);
      throw error;
    }
  },
};

export default forecastApi;
