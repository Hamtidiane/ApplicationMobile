import axios from "axios";

const axiosInstance = axios.create({
    baseURL: "http://localhost:8085", // Base URL du backend
    headers: {
        "Content-Type": "application/json",
    },
});

export default axiosInstance;
