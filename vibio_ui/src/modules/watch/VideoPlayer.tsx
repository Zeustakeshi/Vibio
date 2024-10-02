import ReactPlayer from "react-player";

type Props = {};
const VideoPlayer = (props: Props) => {
    return (
        <div className="overflow-hidden aspect-video w-full rounded-xl">
            <ReactPlayer
                width="100%"
                height="100%"
                controls
                url="https://res.cloudinary.com/dymmvrufy/video/authenticated/s--mI62PmbN--/v1727629038/vibio/videos/dMqVnWamWaviJb80QkR3t.mp4"
            />
        </div>
    );
};

export default VideoPlayer;
