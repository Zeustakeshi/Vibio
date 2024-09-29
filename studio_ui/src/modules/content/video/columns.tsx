import { VideoData } from "@/common/type/video.type";
import { convertVisibilityToText } from "@/lib/utils";
import { ColumnDef } from "@tanstack/react-table";
import VideoRow from "./VideoRow";

export const columns: ColumnDef<VideoData>[] = [
    {
        accessorKey: "video",
        header: "Video",
        cell: ({ row }) => {
            const { video } = row.original;
            return <VideoRow video={video}></VideoRow>;
        },
    },
    {
        accessorKey: "visibility",
        header: "Hiển thị",
        cell: ({ row }) => {
            const { metadata } = row.original;
            return (
                <div className="min-w-[80px] ">
                    {convertVisibilityToText(metadata.visibiliity)}
                </div>
            );
        },
    },
    {
        accessorKey: "createdAt",
        header: "Ngày đăng",
        cell: ({ row }) => {
            const { metadata } = row.original;
            return (
                <div className="min-w-[120px] ">
                    {metadata.createdAt.toISOString()}
                </div>
            );
        },
    },
    {
        accessorKey: "updatedAt",
        header: "Chỉnh sửa làn cuối",
        cell: ({ row }) => {
            const { metadata } = row.original;
            return (
                <div className="min-w-[120px] ">
                    {metadata.updatedAt.toISOString()}
                </div>
            );
        },
    },
    {
        accessorKey: "views",
        header: "Lượt xem",
        cell: ({ row }) => {
            const { metadata } = row.original;
            return <div className="min-w-[70px] ">{metadata.views}</div>;
        },
    },
    {
        accessorKey: "comments",
        header: "Bình luận",
        cell: ({ row }) => {
            const { metadata } = row.original;
            return <div className="min-w-[70px] ">{metadata.comments}</div>;
        },
    },
    {
        accessorKey: "reaction",
        header: "Like (vs dislike)",
        cell: ({ row }) => {
            const { metadata } = row.original;

            const reactPercent = Math.floor(
                (metadata.reaction.like /
                    (metadata.reaction.like + metadata.reaction.dislike)) *
                    100
            );

            return <div className="min-w-[100px] ">{reactPercent} %</div>;
        },
    },
];
