import { Channel } from "../../common/type/channel";
import { Avatar, AvatarImage } from "../../components/ui/avatar";
import { Button } from "../../components/ui/button";
import ChannelAction from "./ChannelAction";

type Props = { channel: Channel };

const ChannelInfomation = ({ channel }: Props) => {
    return (
        <div className="flex w-full justify-start items-center gap-5 my-4">
            <Avatar className="size-[150px]">
                <AvatarImage src={channel?.thumbnail}></AvatarImage>
            </Avatar>
            <div className="flex-col flex justify-start items-start ">
                <h1 className="text-3xl font-semibold mb-2">{channel?.name}</h1>
                <div className="flex justify-start items-center gap-1 text-sm text-muted-foreground font-semibold">
                    <span>@{channel?.name}</span> •
                    <span>{channel?.subscribeCount ?? 0} người đăng ký</span>
                </div>
                {channel?.description && (
                    <div className=" flex items-center justify-start gap-2 text-muted-foreground text-sm">
                        <p className="line-clamp-1 max-w-[320px] ">
                            {channel?.description}
                        </p>
                        <Button variant="link">Xem thêm</Button>
                    </div>
                )}
                {channel && <ChannelAction channel={channel}></ChannelAction>}
            </div>
        </div>
    );
};

export default ChannelInfomation;
