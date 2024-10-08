import { api } from "../lib/api";

export const getFeed = async ({ pageParam = 0 }) => {
    return await api.get("/videos/feeds", {
        params: {
            page: pageParam,
            limit: 20,
        },
    });
};

export const getGuestFeed = async ({ pageParam = 0 }) => {
    return await api.get("/videos/guest/feeds", {
        params: {
            page: pageParam,
            limit: 20,
        },
    });
};
