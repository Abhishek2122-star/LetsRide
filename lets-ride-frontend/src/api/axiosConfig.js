import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080"
});

// Attach JWT token to every request
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");

    if (token) {
      config.headers.Authorization = "Bearer " + token;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Handle API errors
api.interceptors.response.use(
  (response) => response,
  (error) => {

    if (error.response) {
      console.error("API ERROR:", error.response.status, error.response.data);

      // If token expired or unauthorized
      if (error.response.status === 401) {
        localStorage.removeItem("token");
        window.location.href = "/login";
      }
    }

    return Promise.reject(error);
  }
);

export default api;