import { authApi } from "../api/authApi";

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
    const token = await authApi.login(email, password);
    localStorage.setItem("token", token);
    return token;
  },

  signup: async (email: string, password: string): Promise<string> => {
    const token = await authApi.signup(email, password);
    localStorage.setItem("token", token);
    return token;
  },

  logout: (): void => {
    localStorage.removeItem("token");
  },

  getToken: (): string | null => localStorage.getItem("token"),

  isValidAuthToken: (): boolean => {
    const token = localStorage.getItem("token");
    if (!token) return false;
    try {
      // Split the token and decode the payload
      const base64Url = token.split(".")[1];
      const base64 = base64Url.replace("-", "+").replace("_", "/");
      const payload = JSON.parse(window.atob(base64));

      // Check if token is expired
      const currentTime = Math.floor(Date.now() / 1000);
      return payload.exp > currentTime;
    } catch (error) {
      console.log("Error decoding token", error);
      return false;
    }
  },
};
