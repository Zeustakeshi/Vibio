import { z } from "zod";

export const newCommentSchema = z.object({
    content: z
        .string({ message: "Nội dụng bình luận không được bỏ trống!" })
        .max(2000, "Bình luận quá dài"),
    parentId: z.optional(z.string()),
});
