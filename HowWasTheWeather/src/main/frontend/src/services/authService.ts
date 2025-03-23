import { forecastApi } from "../api/forecastApi";

export interface AuthState {
  isAuthenticatedState: boolean;
  user: { email: string } | null;
  loading: boolean;
  error: string | null;
}

export const initialAuthState: AuthState = {
  isAuthenticatedState: !!localStorage.getItem("token"),
  user: null,
  loading: false,
  error: null,
};

export const authService = {
  login: async (email: string, password: string): Promise<string> => {
    try {
      const token = await forecastApi.login(email, password);
      localStorage.setItem("token", token);
      return token;
    } catch (error) {
      throw error;
    }
  },

  signup: async (email: string, password: string): Promise<string> => {
    try {
      const token = await forecastApi.signup(email, password);
      localStorage.setItem("token", token);
      return token;
    } catch (error) {
      throw error;
    }
  },

  logout: (): void => {
    localStorage.removeItem("token");
  },

  isValidAuthToken: (): boolean => {
    return !!localStorage.getItem("token");
  },
};
