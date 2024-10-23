import { Visibility } from "../enum";

export type Video = {
    id: string;
    title: string;
    description: string;
    thumbnail: string;
    viewCount: number;
    likeCount: number;
    dislikeCount: number;
    commentCount: number;
    visibility: Visibility;
    createdAt: Date;
    updatedAt: Date;
};

export type VideoDetail = {
    url: string;
    tags: string[];
    uploadStatus: string;
    allowedComment: boolean;
} & Video;
