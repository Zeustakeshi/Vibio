import { useQuery } from "@tanstack/react-query";
import { getChannel, getChannelGuest } from "../../api/channel";
import { Avatar, AvatarImage } from "../../components/ui/avatar";
import { Button } from "../../components/ui/button";
import { useAuth } from "../../context/AuthContext";
import { useWatchVideo } from "../../routes/watch/$videoId";

type Props = {};

const ChannelAction = (props: Props) => {
    const { video } = useWatchVideo();
    const { isAuthenticated } = useAuth();
    if (!video) return <>loading ....</>;

    const { data } = useQuery({
        queryKey: ["channel", video.channelId],
        queryFn: async () => {
            if (isAuthenticated) return await getChannel(video.channelId);
            else return await getChannelGuest(video.channelId);
        },
        staleTime: 10000,
    });

    return (
        <div className="flex justify-start items-center gap-2">
            <Avatar>
                <AvatarImage src={data?.thumbnail}></AvatarImage>
            </Avatar>
            <div>
                <h4 className="font-semibold text-sm">{data?.name}</h4>
                <p className="text-xs text-muted-foreground">
                    3,34 Tr người đăng ký
                </p>
            </div>
            {data?.subscribed ? (
                <Button variant="secondary" className="rounded-full">
                    Đăng ký
                </Button>
            ) : (
                <Button className="rounded-full">Đăng ký</Button>
            )}
        </div>
    );
};

export default ChannelAction;
