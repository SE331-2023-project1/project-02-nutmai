import axios from "axios";

axios.defaults.headers.common["Authorization"] = `${
  JSON.parse(localStorage.getItem("user") || "{}")?.token
    ? `Bearer ${JSON.parse(localStorage.getItem("user") || "{}")?.token}`
    : ""
}`;

axios.interceptors.request.use(
  (config) => {
    const user = JSON.parse(localStorage.getItem("user") || "{}");
    if (user?.token) config.headers['Authorization'] = `Bearer ${user?.token}`;
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

const axiosClient = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  withCredentials: false,
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
    Authorization: `${
      JSON.parse(localStorage.getItem("user") || "{}")?.token
        ? `Bearer ${JSON.parse(localStorage.getItem("user") || "{}")?.token}`
        : ""
    }`,
  },
});

export default axiosClient;
