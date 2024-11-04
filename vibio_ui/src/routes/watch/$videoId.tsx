import { useMutation, useQuery } from "@tanstack/react-query";
import { createFileRoute } from "@tanstack/react-router";
import { createContext, useContext, useEffect } from "react";
import { getChannel, getChannelGuest } from "../../api/channel";
import { getPlaylistById, getPublicPlaylistById } from "../../api/playlist";
import { getVideoDetail, getVideoDetailGuest } from "../../api/video";
import { Channel } from "../../common/type/channel";
import { VideoDetail } from "../../common/type/video";
import { Skeleton } from "../../components/ui/skeleton";
import Comment from "../../components/video/comment/Comment";
import VideoPlayer from "../../components/video/VideoPlayer";
import { useAuth } from "../../context/AuthContext";
import { usePlaylistControl } from "../../context/PlaylistControlContext";
import ChannelAction from "../../modules/watch/ChannelAction";
import RelatedVideo from "../../modules/watch/RelatedVideo";
import VideoAction from "../../modules/watch/VideoAction";
import VideoDescription from "../../modules/watch/VideoDescription";

export const Route = createFileRoute("/watch/$videoId")({
    validateSearch: (search) => {
        return {
            list: (search.list as string) || "",
        };
    },
    component: WatchVideo,
});

export interface WatchVideoContext {
    video: VideoDetail;
    isLoading: boolean;
    channel?: Channel;
}

const WatchVideoContext = createContext<WatchVideoContext | null>(null);

function WatchVideo() {
    const { videoId } = Route.useParams();
    const { list: playlistId } = Route.useSearch();
    const { isAuthenticated } = useAuth();
    const { setPlaylist, setPlayingVideo } = usePlaylistControl();

    const { mutateAsync: loadPlaylist } = useMutation({
        mutationKey: ["playlist-detail", playlistId],
        mutationFn: () => {
            if (isAuthenticated) return getPlaylistById(playlistId);
            else return getPublicPlaylistById(playlistId);
        },
    });

    const {
        data: video,
        error,
        isLoading,
    } = useQuery({
        queryKey: ["video-detail", videoId],
        queryFn: async () => {
            if (isAuthenticated) return await getVideoDetail(videoId);
            else return await getVideoDetailGuest(videoId);
        },
    });

    const { data: channel } = useQuery({
        queryKey: ["channel", video?.channelId],
        queryFn: async () => {
            if (!video?.channelId) return;
            if (isAuthenticated) return await getChannel(video.channelId);
            else return await getChannelGuest(video.channelId);
        },
        staleTime: 10000,
        enabled: !!video,
    });

    useEffect(() => {
        if (!video) return;
        setPlayingVideo(video);
    }, [video]);

    useEffect(() => {
        if (!playlistId) return;
        (async () => {
            const playlist = await loadPlaylist();
            setPlaylist(playlist);
        })();
    }, [playlistId]);

    if (error) return <div>{JSON.stringify(error)}</div>;

    if (!video)
        return (
            <div className="p-4 flex w-full h-full items-start gap-5">
                <div className="flex-1">
                    {/* video player loading */}
                    <Skeleton className="w-full h-full aspect-video"></Skeleton>
                    {/* video title loading */}
                    <Skeleton className="w-[80%] h-4 rounded-full my-3"></Skeleton>
                    <div className="flex justify-between items-center gap-2">
                        {/* channel action loading */}
                        <div className="flex justify-start items-center gap-2">
                            <Skeleton className="size-[40px] rounded-full"></Skeleton>
                            <Skeleton className="w-[300px] h-8 rounded-xl"></Skeleton>
                        </div>
                        {/* video action loading */}
                        <Skeleton className="w-[300px] h-8 rounded-xl"></Skeleton>
                    </div>
                </div>
                {/* relative video loading */}
                <Skeleton className="xl:block hidden w-[28%] h-full"></Skeleton>
            </div>
        );

    return (
        <WatchVideoContext.Provider value={{ video, isLoading, channel }}>
            <div className="p-4 flex w-full h-full items-start gap-5">
                <div className="flex-1">
                    <VideoPlayer></VideoPlayer>
                    <h2 className="text-xl font-medium my-2 line-clamp-2">
                        {video?.title}
                    </h2>
                    <div className="flex justify-between items-center gap-2">
                        <ChannelAction></ChannelAction>
                        <VideoAction></VideoAction>
                    </div>
                    <VideoDescription></VideoDescription>
                    {video.allowedComment ? (
                        <Comment></Comment>
                    ) : (
                        <p>Tính năng bình luận bị tắt trên</p>
                    )}
                </div>
                <div className="xl:block hidden w-[28%] h-full">
                    <RelatedVideo></RelatedVideo>
                </div>
            </div>
        </WatchVideoContext.Provider>
    );
}

export function useWatchVideo() {
    const context = useContext(WatchVideoContext);
    if (!context) {
        throw new Error(
            "useWatchVideo must be used within an WatchVideoProvider"
        );
    }
    return context;
}
