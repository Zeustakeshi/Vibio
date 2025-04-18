import { ReactionType } from "../common/enum";
import { VideoDetail } from "../common/type/video";
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

export const getVideoDetailGuest = async (
    videoId: string
): Promise<VideoDetail> => {
    return await api.get(`/videos/guest/${videoId}`);
};

export const getVideoDetail = async (videoId: string): Promise<VideoDetail> => {
    return await api.get(`/videos/${videoId}`);
};

export const reactionVideo = async (videoId: string, type: ReactionType) => {
    return await api.post(`/videos/${videoId}/reaction`, {
        reactionType: type,
    });
};

export const unReactionVideo = async (videoId: string, type: ReactionType) => {
    return await api.post(`/videos/${videoId}/un-reaction`, {
        reactionType: type,
    });
};

export const getAllVideoByChannelId = async (
    channelId: string,
    page: number
) => {
    return await api.get(`/videos/guest/channel/${channelId}`, {
        params: { page, limit: 10 },
    });
};
