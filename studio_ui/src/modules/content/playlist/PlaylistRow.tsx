import { Playlist } from "@/common/type/playlist.type";
import DialogConfirmDeletePlaylist from "@/components/dialog/DialogConfirmDeletePlaylist";
import { Button } from "@/components/ui/button";
import { useNavigate } from "@tanstack/react-router";
import { FiEdit3, FiTrash2 } from "react-icons/fi";
import { RxVideo } from "react-icons/rx";
type Props = { playlist: Playlist };

const PlaylistRow = ({ playlist }: Props) => {
    const navigate = useNavigate();
    return (
        <div className="group min-w-[300px] w-full max-w-[450px] flex justify-start items-center gap-2">
            <div className="w-[80px] h-[50px] rounded-md overflow-hidden">
                <img
                    className="w-full h-full object-cover"
                    src={playlist.defaultThumbnail}
                    alt="playlist-thumbnail"
                />
            </div>
            <div className=" w-full h-full flex-1 flex flex-col justify-start items-start">
                <p className="max-w-[80%] text-ellipsis line-clamp-1 font-semibold">
                    {playlist.name}
                </p>
                <div className="min-h-[50px] w-full">
                    <div className="flex justify-start items-center gap-2  transition-all  w-full my-1">
                        <Button
                            onClick={() =>
                                navigate({
                                    to: "/playlist/$playlistId/edit",
                                    params: { playlistId: playlist.id },
                                })
                            }
                            variant="ghost"
                            size="sm"
                        >
                            <FiEdit3 size={16} />
                        </Button>
                        <Button variant="ghost" size="sm">
                            <RxVideo size={16} />
                        </Button>
                        <DialogConfirmDeletePlaylist playlistId={playlist.id}>
                            <Button variant="ghost" size="sm">
                                <FiTrash2 size={16} />
                            </Button>
                        </DialogConfirmDeletePlaylist>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default PlaylistRow;
