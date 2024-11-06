import { Link } from "@tanstack/react-router";
import moment from "moment";
import { forwardRef } from "react";
import { Video } from "../../common/type/video";
import { useAuth } from "../../context/AuthContext";
import DropdownAddVideoToPlaylist from "../../modules/feed/DropdownAddVideoToPlaylist";
import { Avatar, AvatarImage } from "../ui/avatar";

type Props = {
    video: Video;
};

const VideoItem = ({ video }: Props, ref: any) => {
    const { isAuthenticated } = useAuth();
    return (
        <div className="w-auto overflow-hidden rounded-md p-1 z-0">
            <Link
                to="/watch/$videoId"
                params={{ videoId: video?.id }}
                search={{ list: "" }}
                ref={ref}
                className="inline-block rounded-md overflow-hidden aspect-video "
            >
                <img
                    className="w-full h-full object-cover"
                    src={video?.thumbnail}
                    alt={`video-${video?.id}`}
                />
            </Link>
            <div className="flex justify-between items-start gap-1 mt-1">
                <div className="flex justify-start items-start gap-1 ">
                    <Avatar className="size-[32px]">
                        <AvatarImage
                            src={video?.channel?.thumbnail}
                        ></AvatarImage>
                    </Avatar>
                    <div>
                        <p className="line-clamp-2 text-sm font-semibold">
                            {video?.title}
                        </p>
                        <Link
                            to="/"
                            className="text-xs text-muted-foreground line-clamp-2"
                        >
                            {video?.channel?.name}
                        </Link>
                        <div className="flex justify-start items-center gap-2 text-xs text-muted-foreground">
                            <p> {video?.views ?? 0} - Lượt xem</p>
                            <p>{moment(video?.createdAt).fromNow()}</p>
                        </div>
                    </div>
                </div>
                {isAuthenticated && (
                    <DropdownAddVideoToPlaylist
                        videoId={video.id}
                    ></DropdownAddVideoToPlaylist>
                )}
            </div>
        </div>
    );
};

export default forwardRef(VideoItem);
