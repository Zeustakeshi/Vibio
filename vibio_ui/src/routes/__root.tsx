import {
    Outlet,
    createRootRouteWithContext,
    useNavigate,
} from "@tanstack/react-router";
import Header from "../components/header/Header";
import Navbar from "../components/navbar/Navbar";
import { Button } from "../components/ui/button";
import RandomImage from "../components/ui/randomImage";
import WatchPlaylist from "../components/watchPlaylist/WatchPlaylist";
import MaxWidthWrapper from "../components/wrapper/MaxWidthWrapper";
import { AuthContext } from "../context/AuthContext";
import { PlaylistControlProvider } from "../context/PlaylistControlContext";

interface AppRouterContext {
    auth: AuthContext;
}

export const Route = createRootRouteWithContext<AppRouterContext>()({
    notFoundComponent(props) {
        const navigate = useNavigate();
        return (
            <div className="w-full h-full flex-col flex justify-center items-center">
                <RandomImage className="size-[300px]"></RandomImage>
                <p className="text-lg text-muted-foreground my-5">
                    Chúng tôi không tìm thấy địa chỉ bạn cần tìm
                </p>
                <div className="flex justify-center items-center gap-2">
                    <Button
                        onClick={() => window.history.back()}
                        variant="secondary"
                    >
                        Quay lại
                    </Button>
                    <Button onClick={() => navigate({ to: "/" })}>
                        Trang chủ
                    </Button>
                </div>
            </div>
        );
    },
    component: () => (
        <div className="hidden-scroll">
            <PlaylistControlProvider>
                <Header></Header>
                <MaxWidthWrapper className="flex w-full ">
                    <Navbar></Navbar>
                    <div className="flex-1">
                        <Outlet />
                    </div>
                </MaxWidthWrapper>
                <WatchPlaylist></WatchPlaylist>
            </PlaylistControlProvider>
        </div>
    ),
});
