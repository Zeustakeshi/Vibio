import { api } from "../lib/api";

export const validateMemberShipPayment = async (paymentId: string) => {
    return await api.post("payment/member/verify", { paymentId });
};
