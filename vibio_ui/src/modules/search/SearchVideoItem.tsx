import { Link } from "@tanstack/react-router";
import moment from "moment";
import { forwardRef } from "react";
import { SearchVideo } from "../../common/type/video";
import { Avatar, AvatarImage } from "../../components/ui/avatar";

type Props = {
    video: SearchVideo;
};

const SearchVideoItem = ({ video }: Props, ref: any) => {
    return (
        <Link
            to="/watch/$videoId"
            params={{ videoId: video.id }}
            search={{ list: "" }}
            ref={ref}
            className=" w-full flex justify-start items-start gap-5"
        >
            <div className="w-[30%] aspect-video rounded-xl overflow-hidden">
                <img
                    src={video.thumbnail}
                    alt="search-result"
                    className="w-full h-full object-cover"
                />
            </div>
            <div className="w-full max-w-[70%]">
                <h3 className="text-xl font-semibold line-clamp-2 max-w-[100%]">
                    {video.title}
                </h3>

                <div className="flex gap-5 justify-start items-center">
                    <div className="flex justify-start items-center gap-3">
                        <Avatar className="size-[30px]">
                            <AvatarImage
                                src={video.channel.thumbnail}
                            ></AvatarImage>
                        </Avatar>
                        <h4 className=" font-semibold text-muted-foreground">
                            {video.channel.name}
                        </h4>
                    </div>
                    <div className="flex justify-start items-center gap-3 text-muted-foreground my-2">
                        <p>{moment(video.createdAt).toNow()}</p>
                    </div>
                </div>
            </div>
        </Link>
    );
};

export default forwardRef(SearchVideoItem);
