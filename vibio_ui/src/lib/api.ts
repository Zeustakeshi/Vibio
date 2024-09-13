import axios from "axios";

export const api = axios.create({
    baseURL: "http://localhost:8082/api/user",
});

api.interceptors.request.use((request) => {
    // auto asign token here
    return request;
});

api.interceptors.response.use(
    (response) => response.data.data,
    async (error) => {
        console.log({ error });
        return Promise.reject(error?.response?.data?.errors);
    }
);
