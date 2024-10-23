import { Link } from "@tanstack/react-router";
import moment from "moment";
import React from "react";
import { FaPlay } from "react-icons/fa";
import { Visibility } from "../../common/enum";
import { Playlist } from "../../common/type/playlist";

type Props = {
    playlist: Playlist;
};

const PlaylistItem = ({ playlist }: Props, ref: any) => {
    return (
        <div ref={ref} className="w-auto overflow-hidden rounded-md p-1">
            <div className="relative group rounded-md overflow-hidden h-[160px] bg-slate-200">
                <img
                    className="w-full h-full object-cover"
                    src={playlist.defaultThumbnail}
                    alt="playlist-thumbnail"
                />
                <div className="cursor-pointer z-10 abs-center w-full h-full bg-slate-900/70 hidden group-hover:flex justify-center items-center">
                    <div className="flex text-white gap-3 justify-center items-center">
                        <span>
                            <FaPlay />
                        </span>
                        <span className="">Phát tất cả</span>
                    </div>
                </div>
            </div>
            <div className="mt-2 text-sm transition-all">
                <h4 className="text-base font-medium line-clamp-1">
                    {playlist.name}
                </h4>
                <div className="text-muted-foreground font-medium">
                    {playlist.visibility === Visibility.PUBLIC && (
                        <>Công khai</>
                    )}
                    {playlist.visibility === Visibility.PRIVATE && (
                        <>Riêng tư</>
                    )}
                    {playlist.visibility === Visibility.MEMBER && <>Hội viên</>}
                </div>
                <p className="text-muted-foreground text-xs my-1">
                    {moment(playlist.updatedAt).toNow()} -{" "}
                    {playlist.videoCount ?? 0} videos
                </p>
                <Link
                    className="text-sx hover:font-semibold  transition-all"
                    to="/playlists/$playlistId"
                    params={{
                        playlistId: playlist.id,
                    }}
                >
                    Xem toàn bộ danh sách
                </Link>
            </div>
        </div>
    );
};

export default React.forwardRef(PlaylistItem);
