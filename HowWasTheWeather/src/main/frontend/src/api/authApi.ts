import axios from "axios";

const AUTH_BASE_URL = "/api/login";

export const authApi = {
  login: async (email: string, password: string) => {
    console.log("Starting login... email:", email, "password:", password);
    console.log("AUTH_BASE_URL:", AUTH_BASE_URL);
    try {
      const response = await axios.post(
        AUTH_BASE_URL,
        { email, password },
        {
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
          },
        }
      );
      console.log("Login response:", response);
      const token = response.data.token;
      if (!token) {
        throw new Error("No token received");
      }
      return token;
    } catch (error) {
      console.error("Error logging in:", error);
      const message =
        axios.isAxiosError(error) && error.response?.data?.error
          ? error.response.data.error
          : "Login failed";
      throw new Error(message);
    }
  },

  // Need to add this flow and test the backend works
  signup: async (email: string, password: string) => {
    console.log("Starting signup... email:", email, "password:", password);
    try {
      const response = await axios.post("/api/signup", { email, password }); // tried api instead of axios...
      console.log("Signup response:", response);
      const token = response.data.token;
      localStorage.setItem("token", token);
      return token;
    } catch (error) {
      console.error("Error signing up:", error);
      throw error;
    }
  },
};
