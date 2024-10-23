import Cookies from "js-cookie";
import mem from "mem";
import {
    ACCESS_TOKEN_KEY,
    REFRESH_TOKEN_KEY,
    USER_INFO_KEY,
} from "../common/constant/auth";
import { api } from "./api";
import { clearLocalStorage, clearSessionStorage } from "./storage";

const refreshToken = async () => {
    const refreshToken = Cookies.get(REFRESH_TOKEN_KEY);

    if (!refreshToken) {
        sessionStorage.removeItem(USER_INFO_KEY);
        return;
    }
    alert("Refresh token");
    try {
        const data: any = await api({
            method: "POST",
            url: "/token/refresh-token",
            data: {
                token: refreshToken,
            },
        });

        Cookies.set(ACCESS_TOKEN_KEY, data.accessToken.value, {
            expires: new Date(data.accessToken.expiresIn * 1000),
            domain: ".vibio.com",
        });
        Cookies.set(REFRESH_TOKEN_KEY, data.refreshToken.value, {
            expires: new Date(data.refreshToken.expiresIn * 1000),
            domain: ".vibio.com",
        });
    } catch (error) {
        console.log("Refresh token error " + error);
        clearLocalStorage();
        clearSessionStorage();
        Cookies.remove(REFRESH_TOKEN_KEY, { domain: ".vibio.com" });
        Cookies.remove(ACCESS_TOKEN_KEY, { domain: ".vibio.com" });
    }
};

const maxAge = 10000;
export const memoizedRefreshToken = mem(refreshToken, {
    maxAge,
});
