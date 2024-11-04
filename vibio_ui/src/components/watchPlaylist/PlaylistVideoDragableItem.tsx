// import { PlaylistVideo } from "@/common/type/playlist.type";
import { useSortable } from "@dnd-kit/sortable";
import { useNavigate } from "@tanstack/react-router";
import { forwardRef } from "react";
import { TbArrowsMoveVertical } from "react-icons/tb";
import { PlaylistVideo } from "../../common/type/playlist";
import { usePlaylistControl } from "../../context/PlaylistControlContext";
import { cn } from "../../lib/utils";

type Props = {
    id: string;
    video: PlaylistVideo;
};

const PlaylistVideoDragableItem = ({ id, video }: Props, ref: any) => {
    const { attributes, listeners, setNodeRef, transform, transition } =
        useSortable({ id });

    const { playingVideo, playlist } = usePlaylistControl();
    const navigation = useNavigate();

    return (
        <div
            onClick={() => {
                navigation({
                    to: "/watch/$videoId",
                    params: { videoId: video.id },
                    search: { list: playlist?.id ?? "" },
                });
            }}
            style={{
                transform: transform
                    ? `translate3d(${transform.x}px, ${transform.y}px, 0)`
                    : "none",
                transition: transition || undefined,
            }}
            className={cn(
                "flex justify-start items-center gap-2 cursor-pointer my-1",
                { "bg-slate-100 p-1 rounded-md": playingVideo?.id === video.id }
            )}
        >
            <span
                className="p-2"
                ref={setNodeRef}
                {...attributes}
                {...listeners}
            >
                <TbArrowsMoveVertical size={20} />
            </span>
            <div className="flex justify-start items-start gap-2">
                <div
                    ref={ref}
                    className="w-[120px] aspect-video rounded-md overflow-hidden"
                >
                    <img
                        className="w-full h-full object-cover"
                        src={video.thumbnail}
                        alt=""
                    />
                </div>
                <div>
                    <h5 className="font-semibold">{video.title}</h5>
                    <p className="text-sm text-muted-foreground my-1">
                        {video.viewCount} lượt xem
                    </p>
                </div>
            </div>
        </div>
    );
};

export default forwardRef(PlaylistVideoDragableItem);
