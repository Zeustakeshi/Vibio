import { createFileRoute, Link } from "@tanstack/react-router";
import { Avatar, AvatarImage } from "../../components/ui/avatar";

export const Route = createFileRoute("/(app)/")({
    component: Home,
});

function Home() {
    return (
        <div className="grid grid-cols-5 gap-2">
            {new Array(10).fill(0).map((_, index) => (
                <div
                    className="w-[250px] overflow-hidden rounded-md p-1"
                    key={index}
                >
                    <div className="rounded-md overflow-hidden">
                        <img
                            className="w-full h-full object-cover"
                            src="https://i.ytimg.com/vi/XUGywpiiwlE/hq720.jpg?sqp=-oaymwEcCNAFEJQDSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLCYQFiThIyJKl37xfmdDxjk-pCJLA"
                            alt=""
                        />
                    </div>
                    <div className="flex justify-start items-start gap-1 mt-1">
                        <Avatar className="size-[32px]">
                            <AvatarImage src="https://yt3.ggpht.com/MmyBjwWwb50dJo__SPHRO_kGXMjPRhiRgTykElPkjL9xoQSa38kt0_NMvlro4rkRDxnJqSWz=s68-c-k-c0x00ffffff-no-rj"></AvatarImage>
                        </Avatar>
                        <div>
                            <p className="line-clamp-2 text-sm font-semibold">
                                VS Code Extensions tăng hiệu năng làm việc
                                (Giveaway licenses ~400$)
                            </p>
                            <Link
                                to="/"
                                className="text-xs text-muted-foreground"
                            >
                                HoleTex
                            </Link>
                            <div className="flex justify-start items-center gap-2 text-xs text-muted-foreground">
                                <p> 7 N Lượt xem</p>
                                <p>3 tháng trước</p>
                            </div>
                        </div>
                    </div>
                </div>
            ))}
        </div>
    );
}
