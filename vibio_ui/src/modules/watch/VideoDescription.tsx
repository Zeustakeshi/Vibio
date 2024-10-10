import moment from "moment";
import { useState } from "react";
import { cn } from "../../lib/utils";
import { useWatchVideo } from "../../routes/watch/$videoId";
import ChannelAction from "./ChannelAction";

type Props = {};

const VideoDescription = ({}: Props) => {
    const { video } = useWatchVideo();
    const [collapse, setCollapse] = useState<boolean>(true);

    return (
        <div
            className={cn("my-5 bg-slate-100 p-4 rounded-lg transition-all", {
                "h-[130px] overflow-hidden": collapse,
            })}
        >
            <h5 className="font-semibold text-sm space-x-2">
                <span>{video?.viewCount ?? 0} lượt xem</span>
                <span>{moment(video?.updatedAt).fromNow()}</span>
            </h5>
            <p
                className={cn("my-2 min-h-[50px]", {
                    "line-clamp-2": collapse,
                })}
            >
                {video?.description}
            </p>
            {!collapse && (
                <div className="my-4">
                    <ChannelAction></ChannelAction>
                </div>
            )}
            <p
                onClick={() => setCollapse((prev) => !prev)}
                className="text-xs cursor-pointer hover:font-semibold transition-all"
            >
                {collapse ? "Xem thêm" : "Ẩn bớt"}
            </p>
        </div>
    );
};

export default VideoDescription;
