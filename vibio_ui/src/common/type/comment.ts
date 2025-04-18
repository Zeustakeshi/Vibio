import { User } from "./user";

export type Comment = {
    id: string;
    content: string;
    owner: Omit<User, "email">;
    updated: boolean;
    liked: boolean;
    disliked: boolean;
    member: boolean;
    lovedByChannel: boolean;
    likeCount: number;
    dislikeCount: number;
    replyCount: number;
    updatedAt: string;
};
