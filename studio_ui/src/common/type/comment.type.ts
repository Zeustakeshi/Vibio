import { User } from "./user.type";

export type CommentFilterType = "ALL" | "MEMBER" | "UNREPLY";

export type Comment = {
    id: string;
    content: string;
    owner: Omit<User, "email">;
    updated: boolean;
    liked: boolean;
    disliked: boolean;
    lovedByChannel: boolean;
    likeCount: number;
    dislikeCount: number;
    replyCount: number;
    updatedAt: string;
};
