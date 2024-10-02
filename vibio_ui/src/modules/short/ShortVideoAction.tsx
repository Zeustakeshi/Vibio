import { BiDislike, BiLike } from "react-icons/bi";
import { IoMdMore } from "react-icons/io";
import { MdOutlineInsertComment } from "react-icons/md";
import { PiShareFat } from "react-icons/pi";
import { Avatar, AvatarImage } from "../../components/ui/avatar";
import ShortVideoActionItem from "./ShortVideoActionItem";

type Props = {};

const ShortVideoAction = (props: Props) => {
    return (
        <div className="flex flex-col justify-center items-center gap-3">
            <ShortVideoActionItem
                icon={<BiLike size={20}></BiLike>}
                label="32N"
            />
            <ShortVideoActionItem
                icon={<BiDislike size={20}></BiDislike>}
                label="Không thích"
            />

            <ShortVideoActionItem
                icon={
                    <MdOutlineInsertComment size={20}></MdOutlineInsertComment>
                }
                label="Bình luận"
            />

            <ShortVideoActionItem
                icon={<PiShareFat size={20}></PiShareFat>}
                label="Chia sẻ"
            />

            <ShortVideoActionItem icon={<IoMdMore size={20} />} />

            <Avatar>
                <AvatarImage src="https://yt3.ggpht.com/ytc/AIdro_mKzklyPPhghBJQH5H3HpZ108YcE618DBRLAvRUD1AjKNw=s48-c-k-c0x00ffffff-no-rj"></AvatarImage>
            </Avatar>
        </div>
    );
};

export default ShortVideoAction;
