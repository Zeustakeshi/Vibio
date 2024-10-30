import { useQuery } from "@tanstack/react-query";
import { createFileRoute, useNavigate } from "@tanstack/react-router";
import { getChannel, getChannelGuest } from "../../api/channel";
import { useAuth } from "../../context/AuthContext";
import { useToast } from "../../hooks/use-toast";
import ChanneBanner from "../../modules/channel/ChanneBanner";
import ChannelInfomation from "../../modules/channel/ChannelInfomation";
import MemberList from "../../modules/channel/MemberList";

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

    if (!channel) {
        return <>channel</>;
    }

    return (
        <div className="p-4">
            <ChanneBanner backgroundUrl={channel?.background}></ChanneBanner>
            <ChannelInfomation channel={channel}></ChannelInfomation>
            <div className="w-full">
                <MemberList channelId={channel?.id}></MemberList>
            </div>
        </div>
    );
}
