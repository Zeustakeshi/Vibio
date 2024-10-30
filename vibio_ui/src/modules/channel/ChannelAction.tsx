import { useMutation } from "@tanstack/react-query";
import { useNavigate } from "@tanstack/react-router";
import { useEffect, useState } from "react";
import { subscribeChannel, unSubscribeChannel } from "../../api/channel";
import { Channel } from "../../common/type/channel";
import JoinMemberDialog from "../../components/dialog/JoinMemberDialog";
import MemberInfoDialog from "../../components/dialog/MemberInfoDialog";
import { Button } from "../../components/ui/button";
import { useAuth } from "../../context/AuthContext";
import { useToast } from "../../hooks/use-toast";

type Props = { channel: Channel };

const ChannelAction = ({ channel }: Props) => {
    const [isSubscribe, setIsSubscribe] = useState<boolean>(false);

    const navigation = useNavigate();
    const { toast } = useToast();

    const { isAuthenticated } = useAuth();

    useEffect(() => {
        if (!channel) return;
        if (channel?.subscribed) {
            setIsSubscribe(channel.subscribed ?? 0);
        }
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
        } catch (error: any) {
            toast({
                title: "Hủy đăng ký thất bại",
                description: error,
            });
        }
    };
    return (
        <div className="flex justify-start items-center gap-2 mt-2">
            {isSubscribe ? (
                <Button
                    onClick={handleUnSubscribeChannel}
                    variant="outline"
                    disabled={unSubscribeMutation.isPending}
                >
                    {unSubscribeMutation.isPending ? "Đang hủy" : "Hủy đăng ký"}
                </Button>
            ) : (
                <Button
                    onClick={handleSubscribeChannel}
                    disabled={subscribeMutation.isPending}
                >
                    {subscribeMutation.isPending ? "Đang đăng ký" : "đăng ký"}
                </Button>
            )}
            {channel && !channel.member && (
                <JoinMemberDialog channel={channel}>
                    <Button variant="outline">Tham gia hội viên</Button>
                </JoinMemberDialog>
            )}
            {channel && channel.member && (
                <MemberInfoDialog channel={channel}>
                    <Button variant="outline">Thông tin hội viên</Button>
                </MemberInfoDialog>
            )}
        </div>
    );
};

export default ChannelAction;
