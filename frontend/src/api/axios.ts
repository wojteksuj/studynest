import axios from "axios";

const axiosInstance = axios.create({
    baseURL: "/api",
});

axiosInstance.interceptors.request.use((config) => {
    const token = localStorage.getItem("accessToken");

    const isAuthRequest =
        config.url?.includes("/auth/login") ||
        config.url?.includes("/auth/register");

    if (token && !isAuthRequest) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
});

axiosInstance.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401) {
            localStorage.removeItem("accessToken");
            window.location.href = "/";
        }
        return Promise.reject(error);
    }
);

export default axiosInstance;