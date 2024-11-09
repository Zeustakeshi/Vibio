import { useQuery } from "@tanstack/react-query";
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
import { getSubscribedChannels } from "../../api/channel";
import { useAuth } from "../../context/AuthContext";
import { selectApp } from "../../store/features/app/appSlice";
import { Avatar, AvatarImage } from "../ui/avatar";
import { Separator } from "../ui/separator";
import NavbarGroup from "./NavbarGroup";
import NavbarItem from "./NavbarItem";
type Props = {};

const Navbar = ({}: Props) => {
    const { navState } = useSelector(selectApp);
    const { isAuthenticated } = useAuth();

    const { data, isLoading } = useQuery({
        queryKey: ["subscribed-channels"],
        queryFn: () => getSubscribedChannels(0),
        enabled: isAuthenticated,
    });

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
                            to="http://studio.vibio.com:5174"
                            icon={<FaChalkboardUser size={16} />}
                        >
                            Kênh của bạn
                        </NavbarItem>
                        <NavbarItem
                            to="/history"
                            icon={<RiHistoryLine size={20} />}
                        >
                            Video đã xem
                        </NavbarItem>
                        <NavbarItem
                            to="/playlists"
                            icon={<RiPlayListAddLine size={16} />}
                        >
                            Danh sách phát
                        </NavbarItem>
                        <NavbarItem
                            to="http://studio.vibio.com:5174"
                            icon={<GoVideo size={16} />}
                        >
                            Video của bạn
                        </NavbarItem>
                        <NavbarItem
                            to="/playlists/watch-later"
                            icon={<LuClock size={16} />}
                        >
                            Xem sau
                        </NavbarItem>
                        <NavbarItem
                            to="/playlists/liked"
                            icon={<AiOutlineLike size={16} />}
                        >
                            Video đã thích
                        </NavbarItem>
                    </NavbarGroup>
                    <Separator className="my-1" />
                    <NavbarGroup headerLabel="Kênh đã đăng ký">
                        {isLoading && (
                            <div className="w-full h-8 flex justify-center items-center">
                                <span className="size-5 rounded-full border-2 border-primary border-t-transparent animate-spin"></span>
                            </div>
                        )}
                        {data?.content &&
                            data.content.length > 0 &&
                            data.content.map((channel, index) => (
                                <NavbarItem
                                    key={channel.id ?? index}
                                    to={`/channel/${channel.id}`}
                                    icon={
                                        <Avatar className="w-[25px] h-[25px]">
                                            <AvatarImage
                                                src={channel.thumbnail}
                                            ></AvatarImage>
                                        </Avatar>
                                    }
                                >
                                    {channel.name}
                                </NavbarItem>
                            ))}
                    </NavbarGroup>
                    <Separator className="my-1" />
                    <NavbarGroup headerLabel="Khám phá">
                        <NavbarItem
                            to="/trending"
                            icon={<HiOutlineFire size={20} />}
                        >
                            Thịnh hành
                        </NavbarItem>
                        <NavbarItem
                            to="/music"
                            icon={<MdOutlineMusicNote size={20} />}
                        >
                            Âm nhạc
                        </NavbarItem>
                        <NavbarItem
                            to="/gaming"
                            icon={<IoNewspaperOutline size={20} />}
                        >
                            Trò chơi
                        </NavbarItem>
                        <NavbarItem
                            to="/news"
                            icon={<FaChalkboardUser size={20} />}
                        >
                            Tin tức
                        </NavbarItem>
                        <NavbarItem
                            to="/sport"
                            icon={<MdSportsScore size={20} />}
                        >
                            Thể thao
                        </NavbarItem>
                    </NavbarGroup>
                    <Separator className="my-1" />
                    <NavbarGroup headerLabel="Khám phá">
                        <NavbarItem
                            to="/settings"
                            icon={<IoSettingsOutline size={20} />}
                        >
                            Cài đặt
                        </NavbarItem>
                        <NavbarItem
                            to="/reports"
                            icon={<FaRegFlag size={16} />}
                        >
                            Nhật ký báo cáo
                        </NavbarItem>
                        <NavbarItem
                            to="/help-center"
                            icon={<MdHelpOutline size={20} />}
                        >
                            Trợ giúp
                        </NavbarItem>
                        <NavbarItem
                            to="/feedback"
                            icon={<GoReport size={20} />}
                        >
                            Phản hồi
                        </NavbarItem>
                    </NavbarGroup>
                </>
            )}
        </div>
    );
};

export default Navbar;
