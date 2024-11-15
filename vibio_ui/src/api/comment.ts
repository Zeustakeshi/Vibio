import { z } from "zod";
import { ReactionType } from "../common/enum";
import { api } from "../lib/api";
import { newCommentSchema } from "../schema/comment.schema";

export const getCommentByVideoId = async (videoId: string, page: number) => {
    return await api.get(`/videos/${videoId}/comments`, {
        params: {
            page: page,
            limit: 10,
        },
    });
};

export const getCommentGuestByVideoId = async (
    videoId: string,
    page: number
) => {
    return await api.get(`/videos/guest/${videoId}/comments`, {
        params: {
            page: page,
            limit: 10,
        },
    });
};

export const getCommentReplies = async (
    videoId: string,
    page: number,
    commentParent: string
) => {
    return await api.get(`/videos/${videoId}/comments`, {
        params: {
            page: page,
            limit: 10,
            parentId: commentParent,
        },
    });
};

export const getCommentRepliesGuest = async (
    videoId: string,
    page: number,
    commentParent: string
) => {
    return await api.get(`/videos/guest/${videoId}/comments`, {
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
