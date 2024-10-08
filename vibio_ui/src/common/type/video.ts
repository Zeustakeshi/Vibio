import { Channel } from "./channel";

export type Video = {
    id: string;
    title: string;
    thumbnail: string;
    channel: Pick<Channel, "id" | "thumbnail" | "name">;
    createdAt: Date;
    views: number;
};
