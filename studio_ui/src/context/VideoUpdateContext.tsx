import { Form } from "@/components/ui/form";
import { updateVideoSchema } from "@/schema/video.schema";
import { zodResolver } from "@hookform/resolvers/zod";
import { createContext, useCallback, useContext } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";

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
    const form = useForm<z.infer<typeof updateVideoSchema>>({
        resolver: zodResolver(updateVideoSchema),
    });

    const handleUpdateVideo = useCallback(async () => {}, []);

    const value = {};

    return (
        <VideoUpdateProviderContext.Provider {...props} value={value}>
            <Form {...form}>{children}</Form>
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
