import { Channel } from "./channel";

export type Video = {
    id: string;
    thumbnail: string;
    channel: Pick<Channel, "id" | "avatar" | "name">;
    createdAt: Date;
    views: number;
};
