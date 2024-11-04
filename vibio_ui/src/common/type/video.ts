import { ChannelBasicInfo } from "./channel";

export type Video = {
    id: string;
    title: string;
    thumbnail: string;
    channel: Pick<ChannelBasicInfo, "id" | "thumbnail" | "name">;
    createdAt: string;
    views: number;
};

export type VideoDetail = {
    id: string;
    channelId: string;
    url: string;
    title: string;
    description: string;
    viewCount: number;
    likeCount: number;
    thumbnail: string;
    commentCount: number;
    liked: boolean;
    disliked: boolean;
    allowedComment: boolean;
    createdAt: string;
    updatedAt: string;
};

export type SearchVideo = {
    id: string;
    title: string;
    thumbnail: string;
    channel: ChannelBasicInfo;
    updatedAt: string;
    createdAt: string;
};
