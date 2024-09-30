import { AiOutlineLike, AiOutlineThunderbolt } from "react-icons/ai";
import { FaRegFlag } from "react-icons/fa";
import { FaChalkboardUser } from "react-icons/fa6";
import { GoReport, GoVideo } from "react-icons/go";
import { GrChannel, GrHomeRounded } from "react-icons/gr";
import { HiOutlineFire } from "react-icons/hi2";
import { IoNewspaperOutline, IoSettingsOutline } from "react-icons/io5";
import { LuClock } from "react-icons/lu";
import {
    MdHelpOutline,
    MdOutlineMusicNote,
    MdSportsScore,
} from "react-icons/md";
import { RiHistoryLine, RiPlayListAddLine } from "react-icons/ri";
import { Avatar, AvatarImage } from "../ui/avatar";
import { Separator } from "../ui/separator";
import SidebarGroup from "./SidebarGroup";
import SidebarItem from "./SidebarItem";
type Props = {};

const Sidebar = (props: Props) => {
    return (
        <div className="w-[200px] pr-2 py-3 overflow-y-scroll hidden-scroll">
            <SidebarGroup headerLabel="">
                <SidebarItem to="/" icon={<GrHomeRounded size={16} />}>
                    Trang chủ
                </SidebarItem>
                <SidebarItem
                    to="/shorts"
                    icon={<AiOutlineThunderbolt size={20} />}
                >
                    Shorts
                </SidebarItem>
                <SidebarItem
                    to="/feed/subscriptions"
                    icon={<GrChannel size={16} />}
                >
                    Kênh đăng ký
                </SidebarItem>
            </SidebarGroup>
            <Separator className="my-1" />
            <SidebarGroup headerLabel="Cá nhân">
                <SidebarItem to="/asd" icon={<FaChalkboardUser size={16} />}>
                    Kênh của bạn
                </SidebarItem>
                <SidebarItem
                    to="/sdasdfhorts"
                    icon={<RiHistoryLine size={20} />}
                >
                    Video đã xem
                </SidebarItem>
                <SidebarItem
                    to="/feeddsafd/subscriptions"
                    icon={<RiPlayListAddLine size={16} />}
                >
                    Danh sách phát
                </SidebarItem>
                <SidebarItem
                    to="/feedddasf/subscriptions"
                    icon={<GoVideo size={16} />}
                >
                    Video của bạn
                </SidebarItem>
                <SidebarItem
                    to="/feedd/subscriasdfptions"
                    icon={<LuClock size={16} />}
                >
                    Xem sau
                </SidebarItem>
                <SidebarItem
                    to="/feedd/subsadfcriptions"
                    icon={<AiOutlineLike size={16} />}
                >
                    Video đã thích
                </SidebarItem>
            </SidebarGroup>
            <Separator className="my-1" />
            <SidebarGroup headerLabel="Kênh đã đăng ký">
                <SidebarItem
                    to="/feedd/subsadfcriptions"
                    icon={
                        <Avatar className="w-[25px] h-[25px]">
                            <AvatarImage src="https://yt3.ggpht.com/uIccVldUrh4DRnd7hRCXnNoMerzgEA7bcr_zArOQctc9qRWgojQKo0hp558dHCstGI7PjC4idfk=s68-c-k-c0x00ffffff-no-rj"></AvatarImage>
                        </Avatar>
                    }
                >
                    Phạm Minh Hiếu
                </SidebarItem>
                <SidebarItem
                    to="/feedd/subsadfcriptions"
                    icon={
                        <Avatar className="w-[25px] h-[25px]">
                            <AvatarImage src="https://yt3.ggpht.com/uIccVldUrh4DRnd7hRCXnNoMerzgEA7bcr_zArOQctc9qRWgojQKo0hp558dHCstGI7PjC4idfk=s68-c-k-c0x00ffffff-no-rj"></AvatarImage>
                        </Avatar>
                    }
                >
                    Phạm Minh Hiếu
                </SidebarItem>
                <SidebarItem
                    to="/feedd/subsadfcriptions"
                    icon={
                        <Avatar className="w-[25px] h-[25px]">
                            <AvatarImage src="https://yt3.ggpht.com/uIccVldUrh4DRnd7hRCXnNoMerzgEA7bcr_zArOQctc9qRWgojQKo0hp558dHCstGI7PjC4idfk=s68-c-k-c0x00ffffff-no-rj"></AvatarImage>
                        </Avatar>
                    }
                >
                    Phạm Minh Hiếu
                </SidebarItem>
            </SidebarGroup>
            <Separator className="my-1" />
            <SidebarGroup headerLabel="Khám phá">
                <SidebarItem to="/asd" icon={<HiOutlineFire size={20} />}>
                    Thịnh hành
                </SidebarItem>
                <SidebarItem to="/asd" icon={<MdOutlineMusicNote size={20} />}>
                    Âm nhạc
                </SidebarItem>
                <SidebarItem to="/asd" icon={<IoNewspaperOutline size={20} />}>
                    Trò chơi
                </SidebarItem>
                <SidebarItem to="/asd" icon={<FaChalkboardUser size={20} />}>
                    Tin tức
                </SidebarItem>
                <SidebarItem to="/asd" icon={<MdSportsScore size={20} />}>
                    Thể thao
                </SidebarItem>
            </SidebarGroup>
            <Separator className="my-1" />
            <SidebarGroup headerLabel="Khám phá">
                <SidebarItem to="/asd" icon={<IoSettingsOutline size={20} />}>
                    Cài đặt
                </SidebarItem>
                <SidebarItem to="/asd" icon={<FaRegFlag size={16} />}>
                    Nhật ký báo cáo
                </SidebarItem>
                <SidebarItem to="/asd" icon={<MdHelpOutline size={20} />}>
                    Trợ giúp
                </SidebarItem>
                <SidebarItem to="/asd" icon={<GoReport size={20} />}>
                    Phản hồi
                </SidebarItem>
            </SidebarGroup>
        </div>
    );
};

export default Sidebar;
