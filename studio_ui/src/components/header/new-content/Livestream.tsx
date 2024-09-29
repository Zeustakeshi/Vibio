import { DropdownMenuItem } from "@/components/ui/dropdown-menu";
import { CiStreamOn } from "react-icons/ci";

type Props = {};

const Livestream = (props: Props) => {
    return (
        <DropdownMenuItem className="flex w-full  justify-start items-center  gap-3">
            <CiStreamOn size={16} />
            <span>Livestream</span>
        </DropdownMenuItem>
    );
};

export default Livestream;
