import { Visibility } from "../enum";

export type Playlist = {
    id: string;
    name: string;
    description: string;
    videoCount: number;
    updatedAt: string;
    defaultThumbnail: string;
    visibility: Visibility;
};

export type PlaylistVideo = {
    id: string;
    title: string;
    thumbnail: string;
    viewCount: number;
    order: number;
    updatedAt: string;
};
