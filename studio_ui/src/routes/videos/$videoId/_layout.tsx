import Sidebar from "@/components/sidebar/Sidebar";
import SidebarItem from "@/components/sidebar/SidebarItem";
import { Button } from "@/components/ui/button";
import { createFileRoute, Outlet, useNavigate } from "@tanstack/react-router";
import { BiCommentDetail } from "react-icons/bi";
import { FiEdit3 } from "react-icons/fi";
import { MdKeyboardBackspace, MdOutlineAnalytics } from "react-icons/md";

export const Route = createFileRoute("/videos/$videoId/_layout")({
    component: Layout,
});

function Layout() {
    const navigate = useNavigate();
    return (
        <div className="flex justify-start items-start w-full h-[calc(100svh-60px)]">
            <Sidebar>
                <div className="flex justify-start w-full">
                    <Button
                        onClick={() => navigate({ to: "/content/videos" })}
                        size="sm"
                        variant="ghost"
                    >
                        <MdKeyboardBackspace size={20} />
                    </Button>
                </div>
                <div className=" space-y-2  my-4 flex-1 w-full overflow-y-scroll hidden-scroll">
                    <SidebarItem Icon={FiEdit3} to="/videos/1/edit">
                        Chi tiết
                    </SidebarItem>
                    <SidebarItem
                        Icon={MdOutlineAnalytics}
                        to="/videos/1/analytics"
                    >
                        Thống kê
                    </SidebarItem>
                    <SidebarItem Icon={BiCommentDetail} to="/videos/1/comments">
                        Bình luận
                    </SidebarItem>
                </div>
            </Sidebar>
            <div className="flex-1 p-4  h-full">
                <Outlet />
            </div>
        </div>
    );
}
