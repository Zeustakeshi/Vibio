import Sidebar from "@/components/sidebar/Sidebar";
import SidebarItem from "@/components/sidebar/SidebarItem";
import { Button } from "@/components/ui/button";
import { createFileRoute, Outlet, useNavigate } from "@tanstack/react-router";
import { FiEdit3 } from "react-icons/fi";
import { MdKeyboardBackspace } from "react-icons/md";

export const Route = createFileRoute("/playlist/$playlistId/_layout")({
    component: Layout,
});

function Layout() {
    const navigate = useNavigate();
    const { playlistId } = Route.useParams();
    return (
        <div className="flex justify-start items-start w-full h-[calc(100svh-60px)]">
            <Sidebar>
                <div className="flex justify-start w-full">
                    <Button
                        onClick={() =>
                            navigate({
                                to: "/content/playlists",
                                search: { page: 1, limit: 5 },
                            })
                        }
                        size="sm"
                        variant="ghost"
                    >
                        <MdKeyboardBackspace size={20} />
                    </Button>
                </div>
                <div className=" space-y-2  my-4 flex-1 w-full overflow-y-scroll hidden-scroll">
                    <SidebarItem
                        Icon={FiEdit3}
                        to={`/playlist/${playlistId}/edit`}
                    >
                        Chi tiết
                    </SidebarItem>
                </div>
            </Sidebar>
            <div className="flex-1 p-4  h-full">
                <Outlet />
            </div>
        </div>
    );
}