export type Channel = {
    id: string;
    name: string;
    description?: string;
    thumbnail: string;
    background?: string;
    defaultEmail: string;
    createdAt: Date;
    updatedAt: Date;
};

export type ChannelBasicInfo = {
    id: string;
    name: string;
    thumbnail: string;
};
