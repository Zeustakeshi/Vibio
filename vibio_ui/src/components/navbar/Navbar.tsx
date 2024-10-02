import { AiOutlineLike } from "react-icons/ai";
import { FaRegFlag } from "react-icons/fa";
import { FaChalkboardUser } from "react-icons/fa6";
import { GoReport, GoVideo } from "react-icons/go";
import { GrHomeRounded } from "react-icons/gr";
import { HiOutlineFire } from "react-icons/hi2";
import { IoNewspaperOutline, IoSettingsOutline } from "react-icons/io5";
import { LuClock } from "react-icons/lu";
import {
    MdHelpOutline,
    MdOutlineMusicNote,
    MdOutlineVideoLibrary,
    MdSportsScore,
} from "react-icons/md";
import { RiHistoryLine, RiPlayListAddLine } from "react-icons/ri";
import { RxVideo } from "react-icons/rx";
import { useSelector } from "react-redux";
import { selectApp } from "../../store/features/app/appSlice";
import { Avatar, AvatarImage } from "../ui/avatar";
import { Separator } from "../ui/separator";
import NavbarGroup from "./NavbarGroup";
import NavbarItem from "./NavbarItem";
type Props = {};

const Navbar = ({}: Props) => {
    const { navState } = useSelector(selectApp);

    return (
        <div className=" h-[calc(100svh-62px)] top-[62px] sticky w-max pr-2 py-3 overflow-y-scroll hidden-scroll">
            <NavbarGroup headerLabel="">
                <NavbarItem to="/" icon={<GrHomeRounded size={16} />}>
                    Trang chủ
                </NavbarItem>
                <NavbarItem to="/shorts" icon={<RxVideo size={18} />}>
                    Shorts
                </NavbarItem>
                <NavbarItem
                    to="/feed/subscriptions"
                    icon={<MdOutlineVideoLibrary size={20} />}
                >
                    Kênh đăng ký
                </NavbarItem>
            </NavbarGroup>
            {navState === "open" && (
                <>
                    <Separator className="my-1" />
                    <NavbarGroup headerLabel="Cá nhân">
                        <NavbarItem
                            to="/asd"
                            icon={<FaChalkboardUser size={16} />}
                        >
                            Kênh của bạn
                        </NavbarItem>
                        <NavbarItem
                            to="/sdasdfhorts"
                            icon={<RiHistoryLine size={20} />}
                        >
                            Video đã xem
                        </NavbarItem>
                        <NavbarItem
                            to="/feeddsafd/subscriptions"
                            icon={<RiPlayListAddLine size={16} />}
                        >
                            Danh sách phát
                        </NavbarItem>
                        <NavbarItem
                            to="/feedddasf/subscriptions"
                            icon={<GoVideo size={16} />}
                        >
                            Video của bạn
                        </NavbarItem>
                        <NavbarItem
                            to="/feedd/subscriasdfptions"
                            icon={<LuClock size={16} />}
                        >
                            Xem sau
                        </NavbarItem>
                        <NavbarItem
                            to="/feedd/subsadfcriptions"
                            icon={<AiOutlineLike size={16} />}
                        >
                            Video đã thích
                        </NavbarItem>
                    </NavbarGroup>
                    <Separator className="my-1" />
                    <NavbarGroup headerLabel="Kênh đã đăng ký">
                        <NavbarItem
                            to="/feedd/subsadfcriptions"
                            icon={
                                <Avatar className="w-[25px] h-[25px]">
                                    <AvatarImage src="https://yt3.ggpht.com/uIccVldUrh4DRnd7hRCXnNoMerzgEA7bcr_zArOQctc9qRWgojQKo0hp558dHCstGI7PjC4idfk=s68-c-k-c0x00ffffff-no-rj"></AvatarImage>
                                </Avatar>
                            }
                        >
                            Phạm Minh Hiếu
                        </NavbarItem>
                        <NavbarItem
                            to="/feedd/subsadfcriptions"
                            icon={
                                <Avatar className="w-[25px] h-[25px]">
                                    <AvatarImage src="https://yt3.ggpht.com/uIccVldUrh4DRnd7hRCXnNoMerzgEA7bcr_zArOQctc9qRWgojQKo0hp558dHCstGI7PjC4idfk=s68-c-k-c0x00ffffff-no-rj"></AvatarImage>
                                </Avatar>
                            }
                        >
                            Phạm Minh Hiếu
                        </NavbarItem>
                        <NavbarItem
                            to="/feedd/subsadfcriptions"
                            icon={
                                <Avatar className="w-[25px] h-[25px]">
                                    <AvatarImage src="https://yt3.ggpht.com/uIccVldUrh4DRnd7hRCXnNoMerzgEA7bcr_zArOQctc9qRWgojQKo0hp558dHCstGI7PjC4idfk=s68-c-k-c0x00ffffff-no-rj"></AvatarImage>
                                </Avatar>
                            }
                        >
                            Phạm Minh Hiếu
                        </NavbarItem>
                    </NavbarGroup>
                    <Separator className="my-1" />
                    <NavbarGroup headerLabel="Khám phá">
                        <NavbarItem
                            to="/asd"
                            icon={<HiOutlineFire size={20} />}
                        >
                            Thịnh hành
                        </NavbarItem>
                        <NavbarItem
                            to="/asd"
                            icon={<MdOutlineMusicNote size={20} />}
                        >
                            Âm nhạc
                        </NavbarItem>
                        <NavbarItem
                            to="/asd"
                            icon={<IoNewspaperOutline size={20} />}
                        >
                            Trò chơi
                        </NavbarItem>
                        <NavbarItem
                            to="/asd"
                            icon={<FaChalkboardUser size={20} />}
                        >
                            Tin tức
                        </NavbarItem>
                        <NavbarItem
                            to="/asd"
                            icon={<MdSportsScore size={20} />}
                        >
                            Thể thao
                        </NavbarItem>
                    </NavbarGroup>
                    <Separator className="my-1" />
                    <NavbarGroup headerLabel="Khám phá">
                        <NavbarItem
                            to="/asd"
                            icon={<IoSettingsOutline size={20} />}
                        >
                            Cài đặt
                        </NavbarItem>
                        <NavbarItem to="/asd" icon={<FaRegFlag size={16} />}>
                            Nhật ký báo cáo
                        </NavbarItem>
                        <NavbarItem
                            to="/asd"
                            icon={<MdHelpOutline size={20} />}
                        >
                            Trợ giúp
                        </NavbarItem>
                        <NavbarItem to="/asd" icon={<GoReport size={20} />}>
                            Phản hồi
                        </NavbarItem>
                    </NavbarGroup>
                </>
            )}
        </div>
    );
};

export default Navbar;