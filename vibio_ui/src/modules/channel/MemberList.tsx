import { useQuery } from "@tanstack/react-query";
import { getAllMemberByChannelId } from "../../api/member";
import { Avatar, AvatarImage } from "../../components/ui/avatar";
import { Button } from "../../components/ui/button";

type Props = {
    channelId: string;
};

const MemberList = ({ channelId }: Props) => {
    const { data: members } = useQuery({
        queryKey: ["members", channelId],
        queryFn: () => getAllMemberByChannelId(channelId),
        enabled: !!channelId,
    });

    return (
        <div className="w-full px-5 py-2 flex justify-between items-center gap-3 border rounded-md">
            <div>
                <h3 className=" font-semibold">Hội viên của kênh</h3>
                <p className="text-sm text-muted-foreground font-medium">
                    Chân thành cảm ơn các hội viên của kênh!
                </p>
            </div>
            <div className="flex justify-end items-center gap-3">
                {members &&
                    members.content.map((member, index) => (
                        <Avatar key={index}>
                            <AvatarImage src={member.avatar}></AvatarImage>
                        </Avatar>
                    ))}

                <Button variant="secondary">Xem đặc quyền</Button>
            </div>
        </div>
    );
};

export default MemberList;
