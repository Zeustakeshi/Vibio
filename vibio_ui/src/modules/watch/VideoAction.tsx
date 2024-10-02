import { BiDislike, BiLike } from "react-icons/bi";
import { IoMdMore } from "react-icons/io";
import { LuDownload } from "react-icons/lu";
import { TbShare3 } from "react-icons/tb";
import { Button } from "../../components/ui/button";
type Props = {};

const VideoAction = (props: Props) => {
    return (
        <div className="flex justify-end items-center gap-2">
            <div className="rounded-full overflow-hidden flex">
                <Button
                    variant="secondary"
                    className="!rounded-tr-none !rounded-br-none gap-2 text-slate-800"
                >
                    <BiLike size={20}></BiLike>
                    <span>12N</span>
                </Button>
                <Button
                    variant="secondary"
                    className="!rounded-tl-none !rounded-bl-none text-slate-800"
                >
                    <BiDislike size={20} />
                </Button>
            </div>

            <Button
                variant="secondary"
                className="rounded-full gap-2 text-slate-800"
            >
                <TbShare3 size={20} />
                <span>Chia sẻ</span>
            </Button>

            <Button
                variant="secondary"
                className="rounded-full gap-2 text-slate-800"
            >
                <LuDownload size={20} />
                <span>Lưu</span>
            </Button>

            <Button
                variant="secondary"
                className="rounded-full gap-2 text-slate-800"
            >
                <IoMdMore size={20} />
            </Button>
        </div>
    );
};

export default VideoAction;
