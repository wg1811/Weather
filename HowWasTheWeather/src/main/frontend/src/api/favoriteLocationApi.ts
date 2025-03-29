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
  console.log("Sending token:", token); //  Debugging
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
    return Promise.reject(error);
  }
);

export const favoriteLocationApi = {
  getFavorites: async (): Promise<Favorite[]> => {
    const response = await api.get("");
    return response.data;
  },

  addFavorite: async (location: {
    name: string;
    latitude: number;
    longitude: number;
  }): Promise<Favorite> => {
    console.log("Request URL:", api.defaults.baseURL + "");
    console.log("favLocApi - Location added:", location.name);
    const response = await api.post("", location); // Pass the full location object
    console.log("favLocApi - Favorite added:", response);
    return response.data;
  },

  removeFavorite: async (id: number): Promise<void> => {
    await api.delete(`/${id}`);
  },
};

export default favoriteLocationApi;
