import { Avatar, AvatarImage } from "../../components/ui/avatar";
import { Button } from "../../components/ui/button";

type Props = {};

const ChannelAction = (props: Props) => {
    return (
        <div className="flex justify-start items-center gap-2">
            <Avatar>
                <AvatarImage src="https://yt3.ggpht.com/ytc/AIdro_mKzklyPPhghBJQH5H3HpZ108YcE618DBRLAvRUD1AjKNw=s48-c-k-c0x00ffffff-no-rj"></AvatarImage>
            </Avatar>
            <div>
                <h4 className="font-semibold">Fireship</h4>
                <p className="text-xs text-muted-foreground">
                    3,34 Tr người đăng ký
                </p>
            </div>
            <Button className="rounded-full">Đăng ký</Button>
        </div>
    );
};

export default ChannelAction;
