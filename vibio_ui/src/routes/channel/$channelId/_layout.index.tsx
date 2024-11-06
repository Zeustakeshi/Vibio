import { createFileRoute } from "@tanstack/react-router";
import ChannelPlaylist from "../../../modules/channel/home/ChannelPlaylist";
import PopularVideo from "../../../modules/channel/home/PopularVideo";
import VideoForYou from "../../../modules/channel/home/VideoForYou";

export const Route = createFileRoute("/channel/$channelId/_layout/")({
    component: ChannelHomePage,
});

function ChannelHomePage() {
    const { channelId } = Route.useParams();

    return (
        <div>
            <VideoForYou></VideoForYou>
            <PopularVideo></PopularVideo>
            <ChannelPlaylist></ChannelPlaylist>
        </div>
    );
}
