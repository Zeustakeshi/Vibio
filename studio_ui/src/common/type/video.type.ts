export type Video = {
    id: string;
    title: string;
    description: string;
    thumbnail: string;
    viewCount: number;
    likeCount: number;
    dislikeCount: number;
    commentCount: number;
    visibility: VideoVisibility;
    createdAt: Date;
    updatedAt: Date;
};

export type VideoDetail = {
    url: string;
    tags: string[];
    uploadStatus: string;
    allowedComment: boolean;
} & Video;

export enum VideoVisibility {
    PUBLIC = "PUBLIC",
    PRIVATE = "PRIVATE",
    MEMBER = "MEMBER",
}
