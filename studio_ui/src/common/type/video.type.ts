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

export enum VideoVisibility {
    PUBLIC = "PUBLIC",
    PRIVATE = "PRIVATE",
    MEMBER = "MEMBER",
}
