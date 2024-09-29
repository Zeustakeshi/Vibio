export type Video = {
    id: string;
    thumbnail: string;
    title: string;
    description: string;
};

export type VideoMetaData = {
    visibiliity: VideoVisibility;
    createdAt: Date;
    updatedAt: Date;
    views: number;
    comments: number;
    reaction: {
        like: number;
        dislike: number;
    };
};

export type VideoData = {
    video: Video;
    metadata: VideoMetaData;
};

export enum VideoVisibility {
    PUBLIC,
    PRIVATE,
    MEMBER,
}
