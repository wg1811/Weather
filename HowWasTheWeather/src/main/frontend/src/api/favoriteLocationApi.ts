import axios from "axios";

const API_BASE_URL = "/api/favorites"; // Is this right?

interface Favorite {
  id: string;
  name: string;
  latitude: number;
  longitude: number;
}

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

export const favoriteLocationApi = {
  getFavorites: async (): Promise<Favorite[]> => {
    const response = await api.get(""); // Empty string since baseURL is set
    return response.data;
  },

  addFavorite: async (location: {
    name: string;
    latitude: number;
    longitude: number;
  }): Promise<Favorite> => {
    const response = await api.post("", location); // Pass the full location object
    return response.data;
  },

  removeFavorite: async (id: number): Promise<void> => {
    await api.delete(`/${id}`);
  },
};

export default favoriteLocationApi;
