import { IoMdMore } from "react-icons/io";
import AddVideoToPlaylistDialog from "../../components/dialog/AddVideoToPlaylistDialog";
import { Button } from "../../components/ui/button";
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuTrigger,
} from "../../components/ui/dropdown-menu";

type Props = {
    videoId: string;
};

const DropdownAddVideoToPlaylist = ({ videoId }: Props) => {
    return (
        <DropdownMenu>
            <DropdownMenuTrigger asChild>
                <Button className="z-10" size="icon" variant="ghost">
                    <IoMdMore />
                </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent className="max-w-[200px]">
                <AddVideoToPlaylistDialog videoId={videoId}>
                    <Button variant="ghost" className="w-full">
                        Lưu vào danh sách phát
                    </Button>
                </AddVideoToPlaylistDialog>
                <Button variant="ghost" className="w-full">
                    Chia sẻ
                </Button>
                <Button variant="ghost" className="w-full">
                    Không quan tâm
                </Button>
                <Button variant="ghost" className="w-full">
                    Báo cáo vi phạm
                </Button>
            </DropdownMenuContent>
        </DropdownMenu>
    );
};

export default DropdownAddVideoToPlaylist;
