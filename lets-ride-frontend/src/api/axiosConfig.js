import axios from 'axios';

// 1. Create an instance with your Backend URL
const api = axios.create({
    baseURL: 'http://localhost:8080', // Ensure this matches your Spring Boot port
});

// 2. Add a "Request Interceptor"
// This automatically grabs your JWT token from browser storage
// and attaches it to the "Authorization" header for every request.
api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

export default api;