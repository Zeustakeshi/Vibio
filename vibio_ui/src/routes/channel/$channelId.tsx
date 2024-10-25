import { useQuery } from "@tanstack/react-query";
import { createFileRoute, useNavigate } from "@tanstack/react-router";
import { getChannel, getChannelGuest } from "../../api/channel";
import JoinMemberDialog from "../../components/dialog/JoinMemberDialog";
import { Avatar, AvatarImage } from "../../components/ui/avatar";
import { Button } from "../../components/ui/button";
import { useAuth } from "../../context/AuthContext";
import { useToast } from "../../hooks/use-toast";

export const Route = createFileRoute("/channel/$channelId")({
    component: ChannelDetail,
});

function ChannelDetail() {
    const { channelId } = Route.useParams();
    const { isAuthenticated } = useAuth();

    const {
        data: channel,
        isError,
        error,
        isLoading,
    } = useQuery({
        queryKey: ["channel", channelId],
        queryFn: async () => {
            if (isAuthenticated) return await getChannel(channelId);
            else return await getChannelGuest(channelId);
        },
        retryDelay: 1000,
        staleTime: 10000,
    });

    const { toast } = useToast();
    const navigation = useNavigate();

    if (isError) {
        toast({
            title: "Đã có lỗi xảy ra",
            description: error,
        });
        return navigation({ to: "/" });
    }

    if (isLoading) {
        return <div>loading</div>;
    }

    return (
        <div className="p-4">
            {/* Channel banner */}
            <div className="w-full h-[160px] rounded-xl overflow-hidden">
                <img
                    className="w-full h-full object-cover"
                    src={
                        channel?.background ??
                        "https://yt3.googleusercontent.com/gJf0-5yiGCW0ojIG-g-e2xsdWUuan7URbyQBKyu84EDkTybY_7l_TsWLSDcCRWhmIARcFXoDHQ=w1707-fcrop64=1,00005a57ffffa5a8-k-c0xffffffff-no-nd-rj"
                    }
                    alt="channel-banner"
                />
            </div>

            {/* Channel infomation */}
            <div className="flex w-full justify-start items-center gap-5 my-4">
                <Avatar className="size-[150px]">
                    <AvatarImage src={channel?.thumbnail}></AvatarImage>
                </Avatar>
                <div className="flex-col flex justify-start items-start ">
                    <h1 className="text-3xl font-semibold mb-2">
                        {channel?.name}
                    </h1>
                    <div className="flex justify-start items-center gap-1 text-sm text-muted-foreground font-semibold">
                        <span>@{channel?.name}</span> •
                        <span>
                            {channel?.subscribeCount ?? 0} người đăng ký
                        </span>
                    </div>
                    {channel?.description && (
                        <div className=" flex items-center justify-start gap-2 text-muted-foreground text-sm">
                            <p className="line-clamp-1 max-w-[320px] ">
                                {channel?.description}
                            </p>
                            <Button variant="link">Xem thêm</Button>
                        </div>
                    )}
                    <div className="flex justify-start items-center gap-2 mt-2">
                        <Button>Đăng ký</Button>
                        {channel && (
                            <JoinMemberDialog channel={channel}>
                                <Button variant="outline">
                                    Tham gia hội viên
                                </Button>
                            </JoinMemberDialog>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}
