import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080'
});

// 1. Request Interceptor: Attaches the token to every outgoing request
api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

// 2. Response Interceptor: Catches 403/401 errors (Expired Tokens)
api.interceptors.response.use(
    (response) => response,
    (error) => {
        // If the server says 403, the token is likely expired or invalid
        if (error.response && error.response.status === 403) {
            console.error("Token expired or unauthorized. Redirecting to login...");

            // Clear the old, broken token
            localStorage.removeItem('token');

            // Force the user to login again to get a fresh token
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);

export default api;