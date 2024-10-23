import { ReactionType } from "@/common/enum";
import { api } from "@/lib/api";
import { newCommentSchema } from "@/schema/comment.schema";
import { z } from "zod";

export const getAllCommentByVideoId = async (videoId: string, page: number) => {
    return api.get(`/videos/studio/${videoId}/comments`, {
        params: {
            page,
            limit: 10,
        },
    });
};

export const getCommentReplies = async (
    videoId: string,
    page: number,
    commentParent: string
) => {
    return await api.get(`/videos/studio/${videoId}/comments`, {
        params: {
            page: page,
            limit: 10,
            parentId: commentParent,
        },
    });
};

export const createComment = async (
    videoId: string,
    data: z.infer<typeof newCommentSchema>
) => {
    return await api.post(`/videos/${videoId}/comments`, data);
};

export const reactionComment = async (
    videoId: string,
    commentId: string,
    reactionType: ReactionType
) => {
    return await api.post(`/videos/${videoId}/comments/${commentId}/reaction`, {
        reactionType,
    });
};

export const unReactionComment = async (
    videoId: string,
    commentId: string,
    reactionType: ReactionType
) => {
    return await api.post(
        `/videos/${videoId}/comments/${commentId}/un-reaction`,
        { reactionType }
    );
};
