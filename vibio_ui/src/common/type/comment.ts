import { User } from "./user";

export type Comment = {
    id: string;
    content: string;
    owner: Omit<User, "email">;
    updated: boolean;
    liked: boolean;
    disliked: boolean;
    likeCount: number;
    dislikeCount: number;
    replyCount: number;
    updatedAt: string;
};
