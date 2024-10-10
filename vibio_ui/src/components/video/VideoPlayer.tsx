import ReactPlayer from "react-player";

type Props = {};
const VideoPlayer = (props: Props) => {
    return (
        <div className="overflow-hidden aspect-video w-full rounded-xl">
            <ReactPlayer
                width="100%"
                height="100%"
                controls
                url="http://localhost:8088/stream/video"
            />
        </div>
    );
};

export default VideoPlayer;
