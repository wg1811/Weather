import { favoriteLocationApi } from "../api/favoriteLocationApi";

interface Favorite {
  id: string;
  name: string;
  latitude: number;
  longitude: number;
}

export const favoriteService = {
  getFavorites: async (): Promise<Favorite[]> => {
    return await favoriteLocationApi.getFavorites();
  },
  addFavorite: async (location: {
    name: string;
    latitude: number;
    longitude: number;
  }): Promise<Favorite> => {
    console.log("Fovorite Location: ", location);
    return await favoriteLocationApi.addFavorite(location);
  },
  removeFavorite: async (id: number): Promise<void> => {
    await favoriteLocationApi.removeFavorite(id);
  },
};
