import { api } from "@/lib/api";

export const getChannelInfo = async () => {
    return await api.get("/channel/studio/info");
};
