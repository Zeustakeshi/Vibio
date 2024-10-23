import moment from "moment";
import { forwardRef } from "react";
import { PlaylistVideo } from "../../common/type/playlist";

type Props = {
    video: PlaylistVideo;
};

const PlaylistVideoItem = ({ video }: Props, ref: any) => {
    return (
        <div
            ref={ref}
            className="flex justify-start items-start gap-3 hover:bg-slate-100 cursor-pointer transition-all rounded-xl"
        >
            <div className="min-w-[40px] flex items-center justify-end">
                <span>{video.order}</span>
            </div>
            <div className="w-[200px] aspect-video overflow-hidden rounded-xl">
                <img
                    className="w-full h-full object-cover"
                    src={video.thumbnail}
                    alt={`video-thumbnail-${video.id}`}
                />
            </div>
            <div>
                <h5 className="font-semibold text-sm line-clamp-2">
                    {video.title}
                </h5>
                <div className="text-xs text-muted-foreground">
                    <p>
                        <span>{video.viewCount} lượt xem</span>
                        <span>{moment(video.updatedAt).toNow()}</span>
                    </p>
                </div>
            </div>
        </div>
    );
};

export default forwardRef(PlaylistVideoItem);
