import axios from "axios";

const API_BASE_URL = "/api/forecast";

// Auth stuff
const api = axios.create({
  baseURL: API_BASE_URL, //  When I add historical weather, need to add a new base URL?
});

// Attaches jwt
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response.status === 401) {
      console.log("Unauthorized, redirecting to login"); // Need to add a redirect here
    }
    return Promise;
  }
);

export const forecastApi = {
  getForecast: async (location: string) => {
    //console.log("Starting Get Forecast..."); // Degugging
    try {
      const response = await api.get("/getforecast", {
        // Using api.get b/c it has token check.
        params: { location },
      });
      //  console.log("Get Forecast response:", response); // Degugging
      return response.data;
    } catch (error) {
      console.error("Error fetching full forecast:", error);
      throw error;
    }
  },
  getCurrentWeather: async (location: string) => {
    try {
      const response = await axios.get(`${API_BASE_URL}/current`, {
        // does this need to be api.get, too?
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
        // does this need to be api.get, too?
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
        // does this need to be api.get, too?
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
