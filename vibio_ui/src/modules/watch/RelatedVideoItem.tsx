import { Link } from "@tanstack/react-router";
import moment from "moment";
import { forwardRef } from "react";
import { SearchVideo } from "../../common/type/video";

type Props = {
    video: SearchVideo;
};

const RelatedVideoItem = ({ video }: Props, ref: any) => {
    return (
        <Link
            to="/watch/$videoId"
            params={{ videoId: video.id }}
            search={{ list: "" }}
            ref={ref}
            resetScroll
            className="flex justify-start items-start gap-3"
        >
            <div className="w-[120px] min-w-[120px] aspect-video overflow-hidden rounded-xl">
                <img
                    className="w-full h-full object-cover"
                    src={video.thumbnail}
                    alt="related-video"
                />
            </div>
            <div>
                <h5 className="font-semibold text-sm line-clamp-2">
                    {video.title}
                </h5>
                <div className="text-xs text-muted-foreground">
                    <p className="line-clamp-1">{video.channel.name}</p>
                    <p>
                        <span>{moment(video.updatedAt).toNow()}</span>
                    </p>
                </div>
            </div>
        </Link>
    );
};

export default forwardRef(RelatedVideoItem);
