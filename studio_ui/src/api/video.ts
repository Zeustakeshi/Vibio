import { Pageable } from "@/common/type/page.type";
import { Video } from "@/common/type/video.type";
import { api } from "@/lib/api";

export const getAllVideo = async (
    page: number,
    limit: number
): Promise<Pageable<Video>> => {
    return await api.get("/videos/", {
        params: {
            page,
            limit,
        },
    });
};
