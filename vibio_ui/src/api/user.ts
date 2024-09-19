import { api } from "../lib/api";

export const getUserInfo = async () => {
    return await api.get("/user/account/me");
};
