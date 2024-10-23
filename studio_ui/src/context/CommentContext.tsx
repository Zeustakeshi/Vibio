import { createContext, useContext } from "react";

type CommentProviderProps = {
    children: React.ReactNode;
};

type CommentProviderState = {};

const initialState: CommentProviderState = {};

const CommentProviderContext =
    createContext<CommentProviderState>(initialState);

export function CommentProvider({
    children,

    ...props
}: CommentProviderProps) {
    const value = {};

    return (
        <CommentProviderContext.Provider {...props} value={value}>
            {children}
        </CommentProviderContext.Provider>
    );
}

export const useComment = () => {
    const context = useContext(CommentProviderContext);

    if (context === undefined)
        throw new Error("useComment must be used within a CommentProvider");

    return context;
};
