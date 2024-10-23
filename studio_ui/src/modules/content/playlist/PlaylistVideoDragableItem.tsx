import { PlaylistVideo } from "@/common/type/playlist.type";
import { useSortable } from "@dnd-kit/sortable";
import moment from "moment";
import { forwardRef } from "react";

type Props = {
    id: string;
    video: PlaylistVideo;
};

const PlaylistVideoDragableItem = ({ id, video }: Props, ref: any) => {
    const { attributes, listeners, setNodeRef, transform, transition } =
        useSortable({ id: id });

    return (
        <div
            ref={setNodeRef}
            style={{
                transform: transform
                    ? `translate3d(${transform.x}px, ${transform.y}px, 0)`
                    : "none",
                transition: transition || undefined,
            }}
            className="px-3 py-1 cursor-grab flex justify-start items-start gap-3 hover:bg-slate-100 rounded-xl overflow-hidden "
            {...attributes}
            {...listeners}
        >
            <div
                ref={ref}
                className="w-[200px] h-auto aspect-video rounded-xl overflow-hidden"
            >
                <img src={video.thumbnail} alt="" />
            </div>
            <div>
                <p className="text-lg font-semibold">{video.title}</p>
                <p>{video.viewCount ?? 0} lượt xem</p>
                <p>{moment(video.updatedAt).toNow()}</p>
            </div>
        </div>
    );
};

export default forwardRef(PlaylistVideoDragableItem);
