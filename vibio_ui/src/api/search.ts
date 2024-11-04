import { api } from "../lib/api";

export const searchVideo = async (query: string, page: number) => {
    return await api.get("/search/videos", {
        params: {
            query,
            page,
            limit: 10,
        },
    });
};

export const searchVideoAutoComplete = async (
    query: string
): Promise<string[]> => {
    return await api.get("/search/videos/autocomplete", {
        params: { query },
    });
};
