import { VideoVisibility } from "@/common/type/video.type";
import { z } from "zod";

export const MAX_TAG_SIZE = 30;
export const MIN_TAG_SIZE = 5;

export const updateVideoSchema = z.object({
    title: z
        .string({ message: "Tiêu đề video không được để trống" })
        .min(4, "Tiêu đề quá ngắn")
        .max(500, "Tiêu đề quá dài"),
    description: z
        .string({ message: "Mô tả video không được để trống" })
        .min(20, "Mô tả quá ngắn")
        .max(5000, "Mô tả quá dài"),
    visibility: z.enum(Object.values(VideoVisibility) as [string, ...string[]]),
    allowedComment: z.boolean(),
    tags: z
        .array(z.string())
        .min(MIN_TAG_SIZE, "Phải có ít nhất 5 tag cho video này")
        .max(MAX_TAG_SIZE, "Chỉ có thể gắn tối đa 30 thẻ"),
});
