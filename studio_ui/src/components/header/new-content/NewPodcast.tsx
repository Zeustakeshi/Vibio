import { DropdownMenuItem } from "@/components/ui/dropdown-menu";
import { MdPodcasts } from "react-icons/md";

type Props = {};

const NewPodcast = (props: Props) => {
    return (
        <DropdownMenuItem className="flex w-full  justify-start items-center  gap-3">
            <MdPodcasts size={16} />
            <span>Podcast</span>
        </DropdownMenuItem>
    );
};

export default NewPodcast;
