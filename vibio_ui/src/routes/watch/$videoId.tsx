import { useQuery } from "@tanstack/react-query";
import { createFileRoute } from "@tanstack/react-router";
import { createContext, useContext } from "react";
import { getChannel, getChannelGuest } from "../../api/channel";
import { getVideoDetail, getVideoDetailGuest } from "../../api/video";
import { Channel } from "../../common/type/channel";
import { videoDetail } from "../../common/type/video";
import Comment from "../../components/video/comment/Comment";
import VideoPlayer from "../../components/video/VideoPlayer";
import { useAuth } from "../../context/AuthContext";
import ChannelAction from "../../modules/watch/ChannelAction";
import RecommendVideo from "../../modules/watch/RecommendVideo";
import VideoAction from "../../modules/watch/VideoAction";
import VideoDescription from "../../modules/watch/VideoDescription";

export const Route = createFileRoute("/watch/$videoId")({
    component: WatchVideo,
});

export interface WatchVideoContext {
    video: videoDetail;
    isLoading: boolean;
    channel?: Channel;
}

const WatchVideoContext = createContext<WatchVideoContext | null>(null);

function WatchVideo() {
    const { videoId } = Route.useParams();
    const { isAuthenticated } = useAuth();

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
        queryKey: ["channel", video],
        queryFn: async () => {
            if (!video?.channelId) return;
            if (isAuthenticated) return await getChannel(video.channelId);
            else return await getChannelGuest(video.channelId);
        },
        staleTime: 10000,
        enabled: !!video,
    });

    if (error) return <div>{JSON.stringify(error)}</div>;

    if (!video) return <></>;

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
                    <Comment></Comment>
                </div>
                <div className="xl:block hidden w-[28%] h-full">
                    <RecommendVideo></RecommendVideo>
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
