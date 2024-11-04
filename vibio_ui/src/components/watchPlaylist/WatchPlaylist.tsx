import { SiYoutubemusic } from "react-icons/si";
import { usePlaylistControl } from "../../context/PlaylistControlContext";
import { Button } from "../ui/button";
import {
    Sheet,
    SheetContent,
    SheetDescription,
    SheetHeader,
    SheetTitle,
    SheetTrigger,
} from "../ui/sheet";
import PlaylistVideoDragableList from "./PlaylistVideoDragableList";
import VideoControl from "./VideoControl";

type Props = {};

const WatchPlaylist = (props: Props) => {
    const { playlist, playingVideo } = usePlaylistControl();
    return (
        <div className="fixed bottom-10 right-10 ">
            <Sheet>
                <SheetTrigger asChild>
                    <Button
                        className="relative group rounded-full w-min h-min "
                        size="icon"
                    >
                        <span className="animate-spin duration-1000 inline-block p-2">
                            <SiYoutubemusic size="30" />
                        </span>
                        <div className="px-3 py-2 rounded-md bg-primary text-white animate-in animate-out duration-1000 group-hover:block hidden absolute -left-[140px] ">
                            Danh sách phát
                        </div>
                    </Button>
                </SheetTrigger>
                <SheetContent className="">
                    {!playlist && <p>Chưa có danh sách phát nào được chọn</p>}
                    {playlist && (
                        <>
                            <SheetHeader>
                                <SheetTitle>Danh sách phát</SheetTitle>
                                <SheetDescription>
                                    {playlist?.name}
                                </SheetDescription>
                            </SheetHeader>
                            {playingVideo && <VideoControl></VideoControl>}
                            {playlist && (
                                <div className="my-4 overflow-y-scroll overflow-x-hidden custom-scroll h-[60svh] flex flex-col gap-y-3 rounded-md ">
                                    <PlaylistVideoDragableList
                                        playlistId={playlist.id}
                                    ></PlaylistVideoDragableList>
                                </div>
                            )}
                        </>
                    )}
                </SheetContent>
            </Sheet>
        </div>
    );
};

export default WatchPlaylist;
