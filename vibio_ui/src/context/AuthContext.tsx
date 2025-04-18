import { useMutation } from "@tanstack/react-query";
import * as React from "react";
import { getUserInfo } from "../api/user";
import {
    ACCESS_TOKEN_KEY,
    REFRESH_TOKEN_KEY,
    USER_INFO_KEY,
} from "../common/constant/auth";
import { TokenPair } from "../common/type/token";
import { User } from "../common/type/user";
import {
    clearLocalStorage,
    clearSessionStorage,
    getSessionStorageValue,
    saveSessionStorage,
} from "../lib/storage";

import Cookies from "js-cookie";

export interface AuthContext {
    isAuthenticated: boolean;
    login: (tokens: TokenPair) => Promise<void>;
    logout: () => Promise<void>;
    user: User | null;
    authLoading: boolean;
}

const AuthContext = React.createContext<AuthContext | null>(null);

export function AuthProvider({ children }: { children: React.ReactNode }) {
    const [user, setUser] = React.useState<User | null>(null);
    const [isAuthenticated, setIsAuthenticated] = React.useState<boolean>(
        () => !!Cookies.get(REFRESH_TOKEN_KEY)
    );

    const userInfoMutation = useMutation({
        mutationKey: ["get user", "user info"],
        mutationFn: () => getUserInfo(),
    });

    React.useEffect(() => {
        if (!isAuthenticated || !user) loadUser();
    }, [isAuthenticated, user]);

    const logout = React.useCallback(async () => {
        setUser(null);
        setIsAuthenticated(false);
        clearLocalStorage();
        clearSessionStorage();
        Cookies.remove(ACCESS_TOKEN_KEY, {
            domain: ".vibio.com",
        });
        Cookies.remove(REFRESH_TOKEN_KEY, {
            domain: ".vibio.com",
        });
    }, []);

    const loadUser = React.useCallback(async () => {
        // check user from session
        const user = getSessionStorageValue<User>(USER_INFO_KEY);

        if (user) {
            setUser(user);
            setIsAuthenticated(true);
            return;
        }
        try {
            const data: any = await userInfoMutation.mutateAsync();
            saveSessionStorage(USER_INFO_KEY, data);
            setIsAuthenticated(true);
            setUser(data);
        } catch (error) {
            logout();
        }
    }, []);

    const login = React.useCallback(async (tokens: TokenPair) => {
        setIsAuthenticated(true);
        Cookies.set(ACCESS_TOKEN_KEY, tokens.accessToken.value, {
            expires: new Date(tokens.accessToken.expiresIn * 1000),
            domain: ".vibio.com",
        });
        Cookies.set(REFRESH_TOKEN_KEY, tokens.refreshToken.value, {
            expires: new Date(tokens.refreshToken.expiresIn * 1000),
            domain: ".vibio.com",
        });
        loadUser();
    }, []);

    return (
        <AuthContext.Provider
            value={{
                isAuthenticated,
                user,
                authLoading: userInfoMutation.isPending,
                login,
                logout,
            }}
        >
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    const context = React.useContext(AuthContext);
    if (!context) {
        throw new Error("useAuth must be used within an AuthProvider");
    }
    return context;
}
