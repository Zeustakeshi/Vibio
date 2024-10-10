export type ChannelBasicInfo = {
    id: string;
    name: string;
    thumbnail: string;
};

export type Channel = {
    id: string;
    name: string;
    thumbnail: string;
    member: boolean;
    subscribed: boolean;
};
