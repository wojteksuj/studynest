import axios from "axios";

const axiosInstance = axios.create({
    baseURL: "http://localhost:8080/studynest",
});

axiosInstance.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");

    const isAuthEndpoint =
        config.url?.includes("/auth/login") ||
        config.url?.includes("/auth/register");

    if (token && !isAuthEndpoint) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
});

export default axiosInstance;
