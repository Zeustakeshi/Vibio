import { ReactNode } from "@tanstack/react-router";
import {
    createContext,
    SetStateAction,
    useCallback,
    useContext,
    useState,
} from "react";
import { Playlist, PlaylistVideo } from "../common/type/playlist";
import { VideoDetail } from "../common/type/video";

export interface PlaylistControlContext {
    playlist: Playlist | null;
    playingVideo: VideoDetail | null;
    playVideo: boolean;
    videos: PlaylistVideo[];
    setPlaylist: React.Dispatch<SetStateAction<Playlist | null>>;
    setPlayingVideo: React.Dispatch<SetStateAction<VideoDetail | null>>;
    setPlayVideo: React.Dispatch<SetStateAction<boolean>>;
    setVideos: React.Dispatch<SetStateAction<PlaylistVideo[]>>;
    getNextVideo: () => PlaylistVideo | null;
    getPreviousVideo: () => PlaylistVideo | null;
}

const PlaylistControlContext = createContext<PlaylistControlContext | null>(
    null
);

export function PlaylistControlProvider({ children }: { children: ReactNode }) {
    const [playlist, setPlaylist] = useState<Playlist | null>(null);
    const [playingVideo, setPlayingVideo] = useState<VideoDetail | null>(null);
    const [playVideo, setPlayVideo] = useState<boolean>(false);
    const [videos, setVideos] = useState<PlaylistVideo[]>([]);

    const getNextVideo = useCallback(() => {
        const index = videos.findIndex(
            (video) => video.id === playingVideo?.id
        );

        if (index === -1) return null;
        const nextIndex = Math.min(videos.length - 1, index + 1);
        return videos[nextIndex];
    }, [videos, playingVideo]);

    const getPreviousVideo = useCallback(() => {
        const index = videos.findIndex(
            (video) => video.id === playingVideo?.id
        );
        if (index === -1) return null;
        const prevIndex = Math.max(0, index - 1);
        return videos[prevIndex];
    }, [videos, playingVideo]);

    return (
        <PlaylistControlContext.Provider
            value={{
                playlist,
                videos,
                playingVideo,
                playVideo,
                setPlayVideo,
                setPlaylist,
                setPlayingVideo,
                setVideos,
                getNextVideo,
                getPreviousVideo,
            }}
        >
            {children}
        </PlaylistControlContext.Provider>
    );
}

export function usePlaylistControl() {
    const context = useContext(PlaylistControlContext);
    if (!context) {
        throw new Error(
            "usePlaylistControl must be used within an PlaylistControlProvider"
        );
    }
    return context;
}
