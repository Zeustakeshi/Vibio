import { createFileRoute } from "@tanstack/react-router";
import ChannelAction from "../../modules/watch/ChannelAction";
import Comment from "../../modules/watch/comment/Comment";
import RecommendVideo from "../../modules/watch/RecommendVideo";
import VideoAction from "../../modules/watch/VideoAction";
import VideoDescription from "../../modules/watch/VideoDescription";
import VideoPlayer from "../../modules/watch/VideoPlayer";

export const Route = createFileRoute("/watch/$videoId")({
    component: () => (
        <div className="p-4 flex w-full h-full items-start gap-2">
            <div className="flex-1">
                <VideoPlayer></VideoPlayer>
                <h2 className="text-xl font-medium my-2 line-clamp-2">
                    VS Code Extensions tăng hiệu năng làm việc (Giveaway
                    licenses ~400$)
                </h2>
                <div className="flex justify-between items-center gap-2">
                    <ChannelAction></ChannelAction>
                    <VideoAction></VideoAction>
                </div>
                <VideoDescription></VideoDescription>
                <Comment></Comment>
            </div>
            <div className="xl:block hidden w-[30%] h-full">
                <RecommendVideo></RecommendVideo>
            </div>
        </div>
    ),
});
