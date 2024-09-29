import { FiUploadCloud } from "react-icons/fi";
import { Button } from "../../ui/button";
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuTrigger,
} from "../../ui/dropdown-menu";
import Livestream from "./Livestream";
import NewPlaylist from "./NewPlaylist";
import NewPodcast from "./NewPodcast";
import NewPost from "./NewPost";
import NewVideo from "./NewVideo";

type Props = {};

const NewContentTrigger = (props: Props) => {
    return (
        <DropdownMenu>
            <DropdownMenuTrigger asChild>
                <Button
                    variant="outline"
                    className="flex justify-center items-center gap-3"
                >
                    <FiUploadCloud size={20} />
                    <span>Tạo</span>
                </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent
                side="bottom"
                align="end"
                className="w-max min-w-[100px]"
            >
                <NewVideo></NewVideo>
                <Livestream></Livestream>
                <NewPost></NewPost>
                <NewPlaylist></NewPlaylist>
                <NewPodcast></NewPodcast>
            </DropdownMenuContent>
        </DropdownMenu>
    );
};

export default NewContentTrigger;
