import { z } from "zod";
import { Visibility } from "../common/enum";

export const newPlaylistSchema = z.object({
    name: z
        .string({ message: "Tên danh sách phát không được để trống" })
        .min(5, "Tên dánh sách quá ngắn")
        .max(500, "Tên danh sách quá dài"),
    visibility: z.enum(Object.keys(Visibility) as [keyof typeof Visibility]),
});

export const updatePlaylistSchema = newPlaylistSchema;

export const addVideoToPlaylistSchema = z.object({
    playlistId: z.string({ message: "Vui lòng chọn danh sách phát" }),
    videoId: z.string(),
});
