import { getChannelInfo } from "@/api/channel";
import {
    ACCESS_TOKEN_KEY,
    CHANNEL_INFO_KEY,
    REFRESH_TOKEN_KEY,
} from "@/common/constant/auth";
import { Channel } from "@/common/type/channel.type";
import {
    clearLocalStorage,
    clearSessionStorage,
    getSessionStorageValue,
    saveSesstionStorage,
} from "@/lib/storage";
import { useMutation } from "@tanstack/react-query";
import Cookies from "js-cookie";
import React, {
    createContext,
    useCallback,
    useContext,
    useEffect,
    useState,
} from "react";

export interface AuthContext {
    channel: Channel | null;
    isAuthenticated: boolean;
    authLoading: boolean;
}

const AuthContext = createContext<AuthContext | null>(null);

export function AuthProvider({ children }: { children: React.ReactNode }) {
    const [channel, setChannel] = useState<Channel | null>(null);
    const [isAuthenticated, setIsAuthenticated] = useState<boolean>(!!channel);

    const channelInfoMutation = useMutation({
        mutationKey: ["channel info", "get channel"],
        mutationFn: () => getChannelInfo(),
    });

    useEffect(() => {
        if (!isAuthenticated) loadUser();
    }, [isAuthenticated]);

    const logout = useCallback(() => {
        setChannel(null);
        setIsAuthenticated(false);
        clearLocalStorage();
        clearSessionStorage();
        Cookies.remove(ACCESS_TOKEN_KEY);
        Cookies.remove(REFRESH_TOKEN_KEY);
        window.location.href = "http://vibio.com";
    }, []);

    const loadUser = useCallback(async () => {
        // check channel info existed form session stograge
        const channel = getSessionStorageValue<Channel>(CHANNEL_INFO_KEY);

        if (channel) {
            setChannel(channel);
            setIsAuthenticated(true);
            return;
        }

        // load channel info
        try {
            const data: any = await channelInfoMutation.mutateAsync();
            saveSesstionStorage(CHANNEL_INFO_KEY, data);
            setIsAuthenticated(true);
            setChannel(data);
        } catch (error: any) {
            logout();
        }
    }, []);

    return (
        <AuthContext.Provider
            value={{ channel, isAuthenticated, authLoading: false }}
        >
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error("useAuth must be used within an AuthProvider");
    }
    return context;
}
