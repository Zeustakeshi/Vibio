import { ChannelBasicInfo } from "./channel";

export type Video = {
    id: string;
    title: string;
    thumbnail: string;
    channel: Pick<ChannelBasicInfo, "id" | "thumbnail" | "name">;
    createdAt: string;
    views: number;
};

export type videoDetail = {
    id: string;
    channelId: string;
    title: string;
    description: string;
    viewCount: number;
    likeCount: number;
    commentCount: number;
    liked: boolean;
    disliked: boolean;
    allowedComment: boolean;
    createdAt: string;
    updatedAt: string;
};
