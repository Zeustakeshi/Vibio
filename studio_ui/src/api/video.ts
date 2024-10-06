import { Pageable } from "@/common/type/page.type";
import { Video, VideoDetail } from "@/common/type/video.type";
import { api } from "@/lib/api";
import { updateVideoSchema } from "@/schema/video.schema";
import FormData from "form-data";
import { z } from "zod";

const videoPrefix = "/videos/studio";

export const getAllVideo = async (
    page: number,
    limit: number
): Promise<Pageable<Video>> => {
    return await api.get(`${videoPrefix}`, {
        params: {
            page,
            limit,
        },
    });
};

export const uploadVideo = async (data: FormData) => {
    return await api({
        method: "POST",
        url: `${videoPrefix}/upload`,
        data: data,
    });
};

export const getVideoById = async (videoId: string): Promise<VideoDetail> => {
    return await api.get(`${videoPrefix}/${videoId}`);
};

export const updateVideo = async (
    videoId: string,
    video: z.infer<typeof updateVideoSchema>
) => {
    return await api.patch(`${videoPrefix}/${videoId}`, video);
};

export const uploadThumbnail = async (videoId: string, data: FormData) => {
    return await api.post(`${videoPrefix}/${videoId}/thumbnail`, data);
};
