import { createFileRoute, Outlet } from "@tanstack/react-router";
import TrendingNav from "../../../modules/trending/TrendingNav";

export const Route = createFileRoute("/(discovery)/trending/_trending")({
    component: TrendingLayout,
});

function TrendingLayout() {
    return (
        <div>
            <div className="flex justify-start items-center gap-3 mb-5">
                <div className="size-[80px] rounded-full overflow-hidden">
                    <img
                        className="w-full h-full object-cover"
                        src="/images/trending_animated.webp"
                        alt=""
                    />
                </div>
                <h1 className="text-3xl font-semibold">Thịnh hành</h1>
            </div>
            <TrendingNav></TrendingNav>
            <div className="p-5">
                <Outlet></Outlet>
            </div>
        </div>
    );
}
