import { Link } from "@tanstack/react-router";
import moment from "moment";
import { forwardRef } from "react";
import { Video } from "../../common/type/video";
import { Avatar, AvatarImage } from "../../components/ui/avatar";

type Props = {
    video: Video;
};

const FeedItem = ({ video }: Props, ref: any) => {
    return (
        <Link
            to="/watch/$videoId"
            params={{ videoId: video?.id }}
            className="w-auto overflow-hidden rounded-md p-1"
        >
            <div ref={ref} className="rounded-md overflow-hidden">
                <img
                    className="w-full h-full object-cover"
                    src={video?.thumbnail}
                    alt={`video-${video?.id}`}
                />
            </div>
            <div className="flex justify-start items-start gap-1 mt-1">
                <Avatar className="size-[32px]">
                    <AvatarImage src={video?.channel?.thumbnail}></AvatarImage>
                </Avatar>
                <div>
                    <p className="line-clamp-2 text-sm font-semibold">
                        {video?.title}
                    </p>
                    <Link to="/" className="text-xs text-muted-foreground">
                        {video?.channel?.name}
                    </Link>
                    <div className="flex justify-start items-center gap-2 text-xs text-muted-foreground">
                        <p> {video?.views ?? 0} - Lượt xem</p>
                        <p>{moment(video?.createdAt).fromNow()}</p>
                    </div>
                </div>
            </div>
        </Link>
    );
};

export default forwardRef(FeedItem);
