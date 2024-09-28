import axios from "axios";
import Cookies from "js-cookie";
import { ACCESS_TOKEN_KEY } from "../common/constant/auth";
import { memoizedRefreshToken } from "./refreshToken";

export const api = axios.create({
    baseURL: "http://localhost:8080/api/v1/",
});

api.interceptors.request.use((request) => {
    const token = Cookies.get(ACCESS_TOKEN_KEY);
    console.log("hello world");
    if (token) {
        request.headers.Authorization = `Bearer ${token}`;
    }
    return request;
});

api.interceptors.response.use(
    (response) => response.data.data,
    async (error) => {
        console.log({ error });
        if (error.request.status === 401 || error.request.status === 403) {
            await memoizedRefreshToken();
            if (Cookies.get(ACCESS_TOKEN_KEY)) return api(error.config);
        }
        return Promise.reject(error?.response?.data?.errors);
    }
);
