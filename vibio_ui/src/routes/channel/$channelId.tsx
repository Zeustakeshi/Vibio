import { createFileRoute } from "@tanstack/react-router";
import JoinMemberDialog from "../../components/dialog/JoinMemberDialog";
import { Avatar, AvatarImage } from "../../components/ui/avatar";
import { Button } from "../../components/ui/button";

export const Route = createFileRoute("/channel/$channelId")({
    component: ChannelDetail,
});

function ChannelDetail() {
    const { channelId } = Route.useParams();

    return (
        <div className="p-4">
            {/* Channel banner */}
            <div className="w-full h-[160px] rounded-xl overflow-hidden">
                <img
                    className="w-full h-full object-cover"
                    src="https://yt3.googleusercontent.com/gJf0-5yiGCW0ojIG-g-e2xsdWUuan7URbyQBKyu84EDkTybY_7l_TsWLSDcCRWhmIARcFXoDHQ=w1707-fcrop64=1,00005a57ffffa5a8-k-c0xffffffff-no-nd-rj"
                    alt="channel-banner"
                />
            </div>

            {/* Channel infomation */}
            <div className="flex w-full justify-start items-center gap-5 my-4">
                <Avatar className="size-[150px]">
                    <AvatarImage src="https://yt3.googleusercontent.com/nWSdA9GftPmUUpr9p7-uRmzaBpXJPosI-m7anrP040ixXZdMScrMdyordtkR7XBDtewPancSjZo=s160-c-k-c0x00ffffff-no-rj"></AvatarImage>
                </Avatar>
                <div className="flex-col flex justify-start items-start ">
                    <h1 className="text-3xl font-semibold mb-2">THỎ BẢY MÀU</h1>
                    <div className="flex justify-start items-center gap-1 text-sm text-muted-foreground font-semibold">
                        <span>@thobaymauofficial</span> •
                        <span>2,7 Tr người đăng ký</span> •<span>33 video</span>
                    </div>
                    <div className=" flex items-center justify-start gap-2 text-muted-foreground text-sm">
                        <p className="line-clamp-1 max-w-[320px] ">
                            Lorem ipsum dolor sit amet consectetur adipisicing
                            elit. Ducimus sapiente et quisquam totam molestiae
                            ex iusto error neque recusandae pariatur, quasi
                            officiis cum perspiciatis cupiditate maxime
                            excepturi aliquam distinctio delectus.
                        </p>
                        <Button variant="link">Xem thêm</Button>
                    </div>
                    <div className="flex justify-start items-center gap-2">
                        <Button>Đăng ký</Button>
                        <JoinMemberDialog channelId={channelId}>
                            <Button variant="outline">Tham gia hội viên</Button>
                        </JoinMemberDialog>
                    </div>
                </div>
            </div>
        </div>
    );
}
