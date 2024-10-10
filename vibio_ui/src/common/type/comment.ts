import { User } from "./user";

export type Comment = {
    id: string;
    content: string;
    owner: Omit<User, "email">;
    updated: boolean;
    likeCount: number;
    replyCount: number;
    updatedAt: string;
};
