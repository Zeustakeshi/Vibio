import { Video } from "@/common/type/video.type";
import { convertVisibilityToText } from "@/lib/utils";
import { ColumnDef } from "@tanstack/react-table";
import moment from "moment";
import VideoRow from "./VideoRow";

export const columns: ColumnDef<Video>[] = [
    {
        accessorKey: "video",
        header: "Video",
        cell: ({ row }) => {
            const video = row.original;
            return <VideoRow video={video}></VideoRow>;
        },
    },
    {
        accessorKey: "visibility",
        header: "Hiển thị",
        cell: ({ row }) => {
            const video = row.original;
            return (
                <div className="min-w-[80px] ">
                    {convertVisibilityToText(video.visibility)}
                </div>
            );
        },
    },
    {
        accessorKey: "createdAt",
        header: "Ngày đăng",
        cell: ({ row }) => {
            const video = row.original;
            return (
                <div className="min-w-[120px] ">
                    {moment(video.createdAt).format("DD-MM-YYYY")}
                </div>
            );
        },
    },
    {
        accessorKey: "updatedAt",
        header: "Chỉnh sửa làn cuối",
        cell: ({ row }) => {
            const video = row.original;
            return (
                <div className="min-w-[120px] ">
                    {" "}
                    {moment(video.updatedAt).format("DD-MM-YYYY")}
                </div>
            );
        },
    },
    {
        accessorKey: "views",
        header: "Lượt xem",
        cell: ({ row }) => {
            const video = row.original;
            return <div className="min-w-[70px] ">{video.viewCount}</div>;
        },
    },
    {
        accessorKey: "comments",
        header: "Bình luận",
        cell: ({ row }) => {
            const video = row.original;
            return <div className="min-w-[70px] ">{video.commentCount}</div>;
        },
    },
    {
        accessorKey: "reaction",
        header: "Like (vs dislike)",
        cell: ({ row }) => {
            const video = row.original;

            let reactPercent = 0;

            if (video.likeCount !== 0 && video.dislikeCount !== 0) {
                reactPercent = Math.floor(
                    (video.likeCount / (video.likeCount + video.dislikeCount)) *
                        100
                );
            }

            return <div className="min-w-[100px] ">{reactPercent} %</div>;
        },
    },
];
