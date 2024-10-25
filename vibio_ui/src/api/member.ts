import { api } from "../lib/api";

export const joinChannelMember = async (
    channelId: string
): Promise<{ payUrl: string }> => {
    return await api.post(`/channels/${channelId}/members`, {
        paymentMethod: "MOMO",
        redirectUrl: `${window.location.origin}/payment/membership/result`,
    });
};
