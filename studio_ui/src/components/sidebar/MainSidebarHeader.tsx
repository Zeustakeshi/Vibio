import { useAuth } from "@/context/AuthContext";
import { Avatar, AvatarImage } from "../ui/avatar";

type Props = {};

const MainSidebarHeader = (props: Props) => {
    const { channel } = useAuth();

    return (
        <div className="max-w-full w-full flex flex-col justify-center items-center mb-2">
            <Avatar className="w-[90px] h-[90px]">
                <AvatarImage src={channel?.thumbnail}></AvatarImage>
            </Avatar>

            <div className="mt-3 text-center">
                <p className="font-medium">Kênh của bạn</p>
                <p className="text-sm text-muted-foreground max-w-[200px]">
                    {channel?.name}
                </p>
            </div>
        </div>
    );
};

export default MainSidebarHeader;
