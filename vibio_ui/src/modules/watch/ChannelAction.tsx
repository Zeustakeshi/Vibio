import { useMutation, useQuery } from "@tanstack/react-query";
import { useNavigate } from "@tanstack/react-router";
import { useEffect, useState } from "react";
import {
    getChannel,
    getChannelGuest,
    subscribeChannel,
    unSubscribeChannel,
} from "../../api/channel";
import { Avatar, AvatarImage } from "../../components/ui/avatar";
import { Button } from "../../components/ui/button";
import { useAuth } from "../../context/AuthContext";
import { useToast } from "../../hooks/use-toast";
import { useWatchVideo } from "../../routes/watch/$videoId";

type Props = {};

const ChannelAction = (props: Props) => {
    const [isSubscribe, setIsSubscribe] = useState<boolean>(false);

    const { video } = useWatchVideo();
    const { isAuthenticated } = useAuth();
    const navigation = useNavigate();
    const { toast } = useToast();

    if (!video) return <>loading ....</>;

    const { data } = useQuery({
        queryKey: ["channel", video.channelId],
        queryFn: async () => {
            if (isAuthenticated) return await getChannel(video.channelId);
            else return await getChannelGuest(video.channelId);
        },
        staleTime: 10000,
    });

    useEffect(() => {
        if (data?.subscribed) {
            setIsSubscribe(data.subscribed);
        }
    }, [data]);

    const subscribeMutation = useMutation({
        mutationKey: ["subscribe-channel", `subscribe-channel-${data?.id}`],
        mutationFn: (channelId: string) => subscribeChannel(channelId),
    });

    const unSubscribeMutation = useMutation({
        mutationKey: ["unSubscribe-channel", `unSubscribe-channel-${data?.id}`],
        mutationFn: (channelId: string) => unSubscribeChannel(channelId),
    });

    const handleSubscribeChannel = async () => {
        if (!isAuthenticated) {
            navigation({
                to: "/auth/login",
            });
        }
        if (!data?.id) return;
        try {
            await subscribeMutation.mutateAsync(data.id);
            setIsSubscribe(true);
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
        }
        if (!data?.id) return;
        try {
            await unSubscribeMutation.mutateAsync(data.id);
            setIsSubscribe(false);
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
                <AvatarImage src={data?.thumbnail}></AvatarImage>
            </Avatar>
            <div>
                <h4 className="font-semibold text-sm">{data?.name}</h4>
                <p className="text-xs text-muted-foreground">
                    3,34 Tr người đăng ký
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
