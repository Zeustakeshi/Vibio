import { Video } from "@/common/type/video.type";
import { Button } from "@/components/ui/button";
import { useNavigate } from "@tanstack/react-router";
import { BiCommentDetail } from "react-icons/bi";
import { FiEdit3 } from "react-icons/fi";
import { MdOutlineAnalytics } from "react-icons/md";
import { RxVideo } from "react-icons/rx";
type Props = { video: Video };

const VideoRow = ({ video }: Props) => {
    const navigate = useNavigate();
    return (
        <div className="group min-w-[100px] max-w-[450px] flex justify-start items-center gap-2">
            <div className="w-[80px] h-[50px] rounded-md overflow-hidden">
                <img
                    className="w-full h-full object-cover"
                    src={video.thumbnail}
                    alt=""
                />
            </div>
            <div className=" w-full h-full flex-1 flex flex-col justify-start items-start">
                <p className="max-w-[80%] text-ellipsis line-clamp-1 font-semibold">
                    {video.title}
                </p>
                <div className="min-h-[50px]">
                    <p className="group-hover:hidden transition-all line-clamp-3 max-w-[90%] text-xs text-muted-foreground">
                        {video.description}
                    </p>

                    <div className="group-hover:flex justify-start items-center gap-2 hidden transition-all  w-full my-1">
                        <Button
                            onClick={() => navigate({ to: "/videos/1/edit" })}
                            variant="ghost"
                            size="sm"
                        >
                            <FiEdit3 size={16} />
                        </Button>
                        <Button variant="ghost" size="sm">
                            <MdOutlineAnalytics size={16} />
                        </Button>
                        <Button variant="ghost" size="sm">
                            <BiCommentDetail size={16} />
                        </Button>
                        <Button variant="ghost" size="sm">
                            <RxVideo size={16} />
                        </Button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default VideoRow;
