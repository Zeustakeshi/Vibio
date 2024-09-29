import MainSidebarHeader from "@/components/sidebar/MainSidebarHeader";
import Sidebar from "@/components/sidebar/Sidebar";
import SidebarItem from "@/components/sidebar/SidebarItem";
import { createFileRoute, Outlet } from "@tanstack/react-router";
import { GrAnalytics } from "react-icons/gr";
import { IoSettingsOutline } from "react-icons/io5";
import { LuLayoutDashboard } from "react-icons/lu";
import { MdOutlineComment, MdOutlineFeedback } from "react-icons/md";
import { PiVideo } from "react-icons/pi";

export const Route = createFileRoute("/_layout")({
    component: Layout,
});

function Layout() {
    return (
        <div className="flex justify-start items-start w-full h-[calc(100svh-60px)]">
            <Sidebar>
                <MainSidebarHeader></MainSidebarHeader>
                <div className=" space-y-2  my-4 flex-1 w-full overflow-y-scroll hidden-scroll">
                    <SidebarItem Icon={LuLayoutDashboard} to="/">
                        Trang chủ
                    </SidebarItem>
                    <SidebarItem Icon={PiVideo} to="/content/videos">
                        Nội dung
                    </SidebarItem>
                    <SidebarItem Icon={GrAnalytics} to="/analytics">
                        Thống kê
                    </SidebarItem>
                    <SidebarItem Icon={MdOutlineComment} to="/comments">
                        Bình luận
                    </SidebarItem>
                </div>
                <div className="w-full">
                    <SidebarItem Icon={IoSettingsOutline} to="/settings">
                        Cài đặt
                    </SidebarItem>
                    <SidebarItem Icon={MdOutlineFeedback} to="/feedback">
                        Phản hồi
                    </SidebarItem>
                </div>
            </Sidebar>
            <div className="flex-1 p-4  h-full">
                <Outlet />
            </div>
        </div>
    );
}
