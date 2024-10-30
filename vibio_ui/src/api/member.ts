import { Member } from "../common/type/member";
import { Pageable } from "../common/type/page.type";
import { User } from "../common/type/user";
import { api } from "../lib/api";

export const joinChannelMember = async (
    channelId: string
): Promise<{ payUrl: string }> => {
    return await api.post(`/channels/${channelId}/members`, {
        paymentMethod: "MOMO",
        redirectUrl: `${window.location.origin}/payment/membership/result`,
    });
};

export const getMemberInfo = async (channelId: string): Promise<Member> => {
    return await api.get(`/channels/${channelId}/members/info`);
};

export const getAllMemberByChannelId = async (
    channelId: string
): Promise<Pageable<User>> => {
    return await api.get(`/channels/guest/${channelId}/members`, {
        params: {
            page: 0,
            limit: 5,
        },
    });
};
