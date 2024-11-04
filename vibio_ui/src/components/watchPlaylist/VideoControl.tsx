import { useNavigate } from "@tanstack/react-router";
import { IoMdPause, IoMdPlay } from "react-icons/io";
import { MdSkipNext, MdSkipPrevious } from "react-icons/md";
import { usePlaylistControl } from "../../context/PlaylistControlContext";

type Props = {};

const VideoControl = (props: Props) => {
    const {
        playVideo,
        playingVideo,
        setPlayVideo,
        getNextVideo,
        getPreviousVideo,
        playlist,
    } = usePlaylistControl();

    const navigation = useNavigate();

    const handleNextVideo = () => {
        const video = getNextVideo();

        if (!video) return;

        navigation({
            to: "/watch/$videoId",
            params: { videoId: video.id },
            search: { list: playlist?.id ?? "" },
        });
    };

    const handleTogglePlaying = () => {
        setPlayVideo((playing) => !playing);
    };

    const handlePrevVideo = () => {
        const video = getPreviousVideo();
        if (!video) return;

        navigation({
            to: "/watch/$videoId",
            params: { videoId: video.id },
            search: { list: playlist?.id ?? "" },
        });
    };

    return (
        <div className="mt-4 mb-2 flex justify-start items-start gap-2">
            <div className="w-full aspect-video rounded-md overflow-hidden relative">
                <div className="abs-center w-full h-full bg-slate-900/70">
                    <div className="w-full h-full flex justify-center items-center gap-4 text-white">
                        <span
                            onClick={handlePrevVideo}
                            className="cursor-pointer"
                        >
                            <MdSkipPrevious size={40} />
                        </span>
                        <span
                            onClick={handleTogglePlaying}
                            className="cursor-pointer"
                        >
                            {playVideo ? (
                                <IoMdPause size={40} />
                            ) : (
                                <IoMdPlay size={40} />
                            )}
                        </span>
                        <span
                            onClick={handleNextVideo}
                            className="cursor-pointer"
                        >
                            <MdSkipNext size={40} />
                        </span>
                    </div>
                </div>
                <img
                    className="w-full h-full object-cover"
                    src={playingVideo?.thumbnail}
                    alt=""
                />
            </div>
        </div>
    );
};

export default VideoControl;
