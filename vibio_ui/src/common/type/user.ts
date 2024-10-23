import { ChannelBasicInfo } from "./channel";

export type User = {
    id: string;
    username: string;
    channel: ChannelBasicInfo;
    email: string;
    avatar: string;
};
