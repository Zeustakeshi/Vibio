import { useMutation } from "@tanstack/react-query";
import { ReactNode } from "@tanstack/react-router";
import { joinChannelMember } from "../../api/member";
import { Channel } from "../../common/type/channel";
import { useToast } from "../../hooks/use-toast";
import { Avatar, AvatarImage } from "../ui/avatar";
import { Button } from "../ui/button";
import {
    Dialog,
    DialogContent,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "../ui/dialog";

type Props = {
    channel: Channel;
    children: ReactNode;
};

const JoinMemberDialog = ({ children, channel }: Props) => {
    const { mutateAsync, isPending } = useMutation({
        mutationKey: ["join-channel", channel.id],
        mutationFn: () => joinChannelMember(channel.id),
    });

    const { toast } = useToast();

    const handleJoinMember = async () => {
        try {
            const { payUrl } = await mutateAsync();
            window.location.href = payUrl;
        } catch (error: any) {
            toast({
                title: "Tham gia hội viên thất bại",
                description: JSON.stringify(error),
            });
        }
    };

    return (
        <Dialog>
            <DialogTrigger asChild>{children}</DialogTrigger>
            <DialogContent className="min-h-[500px] min-w-[600px] items-start flex flex-col justify-start">
                <DialogHeader>
                    <DialogTitle>Tham gia hội viên</DialogTitle>
                </DialogHeader>
                <div className="max-h-[500px] overflow-y-scroll hidden-scroll w-full ">
                    {/* Banner */}
                    <div className="bg-primary/60 min-h-[200px] w-full rounded-xl overflow-hidden relative p-4">
                        <div className=" abs-center w-full h-full z-0 ">
                            <div className="abs-center w-full h-full bg-slate-900/80"></div>
                            <img
                                className="w-full h-full object-cover "
                                src={
                                    channel.background ??
                                    "https://yt3.googleusercontent.com/gJf0-5yiGCW0ojIG-g-e2xsdWUuan7URbyQBKyu84EDkTybY_7l_TsWLSDcCRWhmIARcFXoDHQ=w1707-fcrop64=1,00005a57ffffa5a8-k-c0xffffffff-no-nd-rj"
                                }
                                alt=""
                            />
                        </div>
                        <div className="abs-center w-full h-full p-5">
                            <Avatar className="size-[80px]">
                                <AvatarImage
                                    src={channel.thumbnail}
                                    alt="channel-avatar"
                                ></AvatarImage>
                            </Avatar>
                            <div className="mt-4  text-white">
                                <p className="text-lg font-semibold ">
                                    {channel.name}
                                </p>
                                <h3 className="text-xl font-semibold">
                                    Tham gia kênh này
                                </h3>
                                <p className="text-sm">
                                    Sử dụng các đặc quyền dành cho hội viên
                                </p>
                            </div>
                        </div>
                    </div>
                    <div className="mt-3">
                        <h3 className="font-semibold text-lg">
                            Thành viên VIP
                        </h3>
                        <p className="text-muted-foreground font-semibold text-sm">
                            30.000 ₫/tháng
                        </p>
                    </div>
                    <div className="mt-3">
                        <h3 className="font-semibold text-lg">Đặc quyền</h3>
                        <ul className="pl-5 space-y-2 py-2 text-sm text-muted-foreground">
                            <li>- Xem video dành riêng cho hội viên</li>
                            <li>
                                - Bạn sẽ được thông báo sớm nhất khi có video
                                mới
                            </li>
                            <li>
                                - Huy hiệu người hâm mộ trung thành nằm cạnh tên
                                của bạn trong phần bình luận và cuộc trò chuyện
                                trực tiếp.
                            </li>
                            <li>
                                - Bình luận của bạn sẽ được đọc và trả lời trước
                            </li>
                        </ul>
                    </div>
                </div>
                <Button
                    onClick={handleJoinMember}
                    disabled={isPending}
                    className="ml-auto mt-2"
                >
                    {isPending ? "Đang xử lý" : "Tham gia ngay"}
                </Button>
            </DialogContent>
        </Dialog>
    );
};

export default JoinMemberDialog;
