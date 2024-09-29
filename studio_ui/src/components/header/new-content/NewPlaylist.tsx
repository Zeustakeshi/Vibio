import { DropdownMenuItem } from "@/components/ui/dropdown-menu";
import { RiPlayListAddFill } from "react-icons/ri";

type Props = {};

const NewPlaylist = (props: Props) => {
    return (
        <DropdownMenuItem className="flex w-full  justify-start items-center  gap-3">
            <RiPlayListAddFill size={16} />
            <span>Danh sách phát mới</span>
        </DropdownMenuItem>
    );
};

export default NewPlaylist;
