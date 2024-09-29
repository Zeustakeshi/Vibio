import { DropdownMenuItem } from "@/components/ui/dropdown-menu";
import { FiEdit3 } from "react-icons/fi";

type Props = {};

const NewPost = (props: Props) => {
    return (
        <DropdownMenuItem className="flex w-full  justify-start items-center  gap-3">
            <FiEdit3 size={16} />
            <span>Tạo bài viết</span>
        </DropdownMenuItem>
    );
};

export default NewPost;
