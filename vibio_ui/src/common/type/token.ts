export enum TokenType {
    ACCESS_TOKEN,
    REFRESH_TOKEN,
}

export type Token = {
    id: string;
    value: string;
    type: TokenType;
    expiresIn: number;
};

export type TokenPair = {
    accessToken: Token;
    refreshToken: Token;
};
