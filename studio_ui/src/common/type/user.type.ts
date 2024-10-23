import { ChannelBasicInfo } from "./channel.type";

export type User = {
    id: string;
    username: string;
    channel: ChannelBasicInfo;
    email: string;
    avatar: string;
};
