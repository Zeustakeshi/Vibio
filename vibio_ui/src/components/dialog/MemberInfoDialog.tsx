import { useQuery } from "@tanstack/react-query";
import { ReactNode } from "@tanstack/react-router";
import moment from "moment";
import { getMemberInfo } from "../../api/member";
import { Channel } from "../../common/type/channel";
import { useAuth } from "../../context/AuthContext";
import { Avatar, AvatarImage } from "../ui/avatar";
import {
    Dialog,
    DialogContent,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "../ui/dialog";

type Props = {
    children: ReactNode;
    channel: Channel;
};

const MemberInfoDialog = ({ channel, children }: Props) => {
    const { user } = useAuth();
    const { data: member, isLoading } = useQuery({
        queryKey: ["member-info", channel.id],
        queryFn: () => getMemberInfo(channel.id),
    });

    return (
        <Dialog>
            <DialogTrigger asChild>{children}</DialogTrigger>
            <DialogContent className="min-h-[300px] min-w-[600px] items-start flex flex-col justify-start">
                <DialogHeader>
                    <DialogTitle>Thẻ hội viên</DialogTitle>
                </DialogHeader>
                <div className="w-full h-[250px] rounded-xl overflow-hidden bg-blue-500 relative">
                    <img
                        className="w-full h-full object-cover"
                        src={
                            channel?.background ??
                            "https://yt3.googleusercontent.com/gJf0-5yiGCW0ojIG-g-e2xsdWUuan7URbyQBKyu84EDkTybY_7l_TsWLSDcCRWhmIARcFXoDHQ=w1707-fcrop64=1,00005a57ffffa5a8-k-c0xffffffff-no-nd-rj"
                        }
                        alt="channel-banner"
                    />
                    <div className="abs-center w-full h-full bg-slate-900/80 backdrop-blur-sm "></div>
                    <div className=" abs-center w-full h-full p-5">
                        <div className="flex items-center mb-4">
                            <Avatar className="size-[100px]">
                                <AvatarImage src={user?.avatar}></AvatarImage>
                            </Avatar>
                            <div className="ml-4 text-white">
                                <h2 className="text-xl font-bold ">
                                    {user?.username}
                                </h2>
                                <p className="text-slate-300 line-clamp-2">
                                    Hội viên kênh {channel.name}
                                </p>
                            </div>
                        </div>
                        <div className="border-t border-gray-200 mt-4 pt-4">
                            <p className="text-sm text-slate-300">
                                Ngày Tham Gia:{" "}
                                <span className="font-semibold">
                                    {moment(member?.createdAt).format(
                                        "DD/MM/YYYY"
                                    )}
                                </span>
                            </p>
                            <p className="text-sm text-slate-300">
                                Ngày Hết Hạn:{" "}
                                <span className="font-semibold">
                                    {moment(member?.expireDate).format(
                                        "DD/MM/YYYY"
                                    )}
                                </span>
                            </p>
                        </div>
                    </div>
                </div>
            </DialogContent>
        </Dialog>
    );
};

export default MemberInfoDialog;
