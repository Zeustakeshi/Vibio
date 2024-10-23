import { useMutation } from "@tanstack/react-query";
import { useNavigate } from "@tanstack/react-router";
import { useEffect, useState } from "react";
import { subscribeChannel, unSubscribeChannel } from "../../api/channel";
import { Avatar, AvatarImage } from "../../components/ui/avatar";
import { Button } from "../../components/ui/button";
import { useAuth } from "../../context/AuthContext";
import { useToast } from "../../hooks/use-toast";
import { useWatchVideo } from "../../routes/watch/$videoId";

type Props = {};

const ChannelAction = (props: Props) => {
    const [isSubscribe, setIsSubscribe] = useState<boolean>(false);
    const [subscribeCount, setSubscribeCount] = useState<number>(0);

    const { video, channel } = useWatchVideo();
    const { isAuthenticated } = useAuth();
    const navigation = useNavigate();
    const { toast } = useToast();

    if (!video) return <>loading ....</>;

    useEffect(() => {
        if (!channel) return;
        if (channel?.subscribed) {
            setIsSubscribe(channel.subscribed ?? 0);
        }

        setSubscribeCount(channel.subscribeCount);
    }, [channel]);

    const subscribeMutation = useMutation({
        mutationKey: ["subscribe-channel", `subscribe-channel-${channel?.id}`],
        mutationFn: (channelId: string) => subscribeChannel(channelId),
    });

    const unSubscribeMutation = useMutation({
        mutationKey: [
            "unSubscribe-channel",
            `unSubscribe-channel-${channel?.id}`,
        ],
        mutationFn: (channelId: string) => unSubscribeChannel(channelId),
    });

    const handleSubscribeChannel = async () => {
        if (!isAuthenticated) {
            navigation({
                to: "/auth/login",
            });
            return;
        }
        if (!channel?.id) return;
        try {
            await subscribeMutation.mutateAsync(channel.id);
            setIsSubscribe(true);
            setSubscribeCount((sub) => sub + 1);
        } catch (error: any) {
            toast({
                title: "Đăng ký thất bại",
                description: error,
            });
        }
    };

    const handleUnSubscribeChannel = async () => {
        if (!isAuthenticated) {
            navigation({
                to: "/auth/login",
            });
            return;
        }
        if (!channel?.id) return;
        try {
            await unSubscribeMutation.mutateAsync(channel.id);
            setIsSubscribe(false);
            setSubscribeCount((sub) => sub - 1);
        } catch (error: any) {
            toast({
                title: "Hủy đăng ký thất bại",
                description: error,
            });
        }
    };

    return (
        <div className="flex justify-start items-center gap-2">
            <Avatar>
                <AvatarImage src={channel?.thumbnail}></AvatarImage>
            </Avatar>
            <div>
                <h4 className="font-semibold text-sm">{channel?.name}</h4>
                <p className="text-xs text-muted-foreground">
                    {subscribeCount} người đăng ký
                </p>
            </div>
            {isSubscribe ? (
                <Button
                    onClick={handleUnSubscribeChannel}
                    variant="secondary"
                    className="rounded-full"
                    disabled={unSubscribeMutation.isPending}
                >
                    {unSubscribeMutation.isPending ? "Đang hủy" : "Hủy đăng ký"}
                </Button>
            ) : (
                <Button
                    onClick={handleSubscribeChannel}
                    className="rounded-full"
                    disabled={subscribeMutation.isPending}
                >
                    {subscribeMutation.isPending ? "Đang đăng ký" : "đăng ký"}
                </Button>
            )}
        </div>
    );
};

export default ChannelAction;
