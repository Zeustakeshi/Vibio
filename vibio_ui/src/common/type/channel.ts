export type ChannelBasicInfo = {
    id: string;
    name: string;
    thumbnail: string;
};

export type Channel = {
    id: string;
    name: string;
    accountId: string;
    background: string;
    thumbnail: string;
    member: boolean;
    subscribeCount: number;
    subscribed: boolean;
    description: string;
};

export type ChannelDetail = {
    id: string;
    name: string;
    description: string;
    thumbnail: string;
    background: string;
    defaultEmail: string;
    createdAt: string;
    updatedAt: string;
};
