import { useNavigate } from "@tanstack/react-router";
import ReactPlayer from "react-player";
import { usePlaylistControl } from "../../context/PlaylistControlContext";
import { useWatchVideo } from "../../routes/watch/$videoId";

type Props = {};
const VideoPlayer = ({}: Props) => {
    const { video } = useWatchVideo();
    const { playVideo, playlist, setPlayVideo, getNextVideo } =
        usePlaylistControl();

    const navigation = useNavigate();

    return (
        <div className="overflow-hidden aspect-video w-full rounded-xl">
            <ReactPlayer
                config={{}}
                width="100%"
                height="100%"
                controls
                playing={playVideo}
                onPause={() => setPlayVideo(false)}
                onPlay={() => setPlayVideo(true)}
                onEnded={() => {
                    const video = getNextVideo();
                    if (!video) return;
                    navigation({
                        to: "/watch/$videoId",
                        params: { videoId: video.id },
                        search: { list: playlist?.id ?? "" },
                    });
                }}
                url={video.url}
            />
        </div>
    );
};

export default VideoPlayer;
