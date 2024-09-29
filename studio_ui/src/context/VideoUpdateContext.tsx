import { createContext, useContext } from "react";

type VideoUpdateProviderProps = {
    children: React.ReactNode;
    videoId: string;
};

type VideoUpdateProviderState = {};

const initialState: VideoUpdateProviderState = {};

const VideoUpdateProviderContext =
    createContext<VideoUpdateProviderState>(initialState);

export function VideoUpdateProvider({
    children,
    videoId,
    ...props
}: VideoUpdateProviderProps) {
    const value = {};

    return (
        <VideoUpdateProviderContext.Provider {...props} value={value}>
            {children}
        </VideoUpdateProviderContext.Provider>
    );
}

export const useVideoUpdate = () => {
    const context = useContext(VideoUpdateProviderContext);

    if (context === undefined)
        throw new Error(
            "useVideoUpdate must be used within a VideoUpdateProvider"
        );

    return context;
};
