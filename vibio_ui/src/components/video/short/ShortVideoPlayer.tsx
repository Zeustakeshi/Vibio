import { useEffect, useState } from "react";
import { FaPause, FaPlay } from "react-icons/fa";
import ReactPlayer from "react-player";
import { Avatar, AvatarImage } from "../../ui/avatar";
import { Button } from "../../ui/button";

type Props = {
    url: string;
    isActive?: boolean;
};

const ShortVideoPlayer = ({ url, isActive = false }: Props) => {
    const [play, setPlay] = useState<boolean>(false);

    useEffect(() => {
        setPlay(isActive);
    }, [isActive]);

    return (
        <div className=" relative h-[85svh] w-auto transition-all ">
            <div className="absolute top-5 left-5 z-10">
                <div
                    onClick={() => setPlay((play) => !play)}
                    className="cursor-pointer bg-slate-900/20 backdrop-blur-md size-[35px] rounded-full flex justify-center items-center text-white"
                >
                    {!play && <FaPlay size={16}></FaPlay>}
                    {play && <FaPause size={16}></FaPause>}
                </div>
            </div>
            <div
                onClick={() => {
                    setPlay((play) => !play);
                }}
                className=" w-auto h-[85svh] aspect-[9/16] rounded-xl overflow-hidden "
            >
                <ReactPlayer
                    key={+isActive}
                    loop
                    className="!w-auto !h-[85svh] !aspect-[9/16]"
                    playing={play}
                    url={url}
                />
            </div>

            <div className="absolute bottom-2 left-2 p-2 rounded-md bg-white/20 backdrop-blur-md">
                <div className="flex justify-start gap-2 items-center">
                    <Avatar className="size-[30px]">
                        <AvatarImage src="https://yt3.ggpht.com/ytc/AIdro_mKzklyPPhghBJQH5H3HpZ108YcE618DBRLAvRUD1AjKNw=s48-c-k-c0x00ffffff-no-rj"></AvatarImage>
                    </Avatar>
                    <h3 className="text-white font-semibold text-sm">
                        Fireship
                    </h3>
                    <Button size="sm">Đăng ký</Button>
                </div>
            </div>
        </div>
    );
};

export default ShortVideoPlayer;
