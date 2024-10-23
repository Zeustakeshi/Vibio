import { Playlist } from "@/common/type/playlist.type";
import { convertVisibilityToText } from "@/lib/utils";
import { ColumnDef } from "@tanstack/react-table";
import moment from "moment";
import PlaylistRow from "./PlaylistRow";

export const columns: ColumnDef<Playlist>[] = [
    {
        accessorKey: "playlist",
        header: "Playlist",
        cell: ({ row }) => {
            const playlist = row.original;
            return <PlaylistRow playlist={playlist}></PlaylistRow>;
        },
    },
    {
        accessorKey: "visibility",
        header: "Hiển thị",
        cell: ({ row }) => {
            const playlist = row.original;
            return (
                <div className="min-w-[80px] ">
                    {convertVisibilityToText(playlist.visibility)}
                </div>
            );
        },
    },
    {
        accessorKey: "updatedAt",
        header: "Cập nhật lần cuối",
        cell: ({ row }) => {
            const playlist = row.original;
            return (
                <div className="min-w-[120px] ">
                    {moment(playlist.updatedAt).format("DD-MM-YYYY")}
                </div>
            );
        },
    },
    {
        accessorKey: "videoCount",
        header: "Số video",
        cell: ({ row }) => {
            const playlist = row.original;
            return (
                <div className="min-w-[70px] ">{playlist.videoCount ?? 0}</div>
            );
        },
    },
];
