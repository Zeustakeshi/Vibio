import { Channel } from "../common/type/channel";
import { api } from "../lib/api";

export const getChannel = async (channelId: string): Promise<Channel> => {
    return await api.get(`/channels/${channelId}`);
};

export const getChannelGuest = async (channelId: string): Promise<Channel> => {
    return await api.get(`/channels/guest/${channelId}`);
};
