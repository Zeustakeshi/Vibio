import { buttonVariants } from "@/components/ui/button";
import { cn } from "@/lib/utils";
import { Link } from "@tanstack/react-router";

type Props = {
    videoId: string;
};

const CommentFilter = ({ videoId }: Props) => {
    return (
        <div className="my-4 flex justify-start items-center gap-2">
            <Link
                to="/videos/$videoId/comments"
                params={{ videoId: videoId }}
                activeOptions={{ includeHash: false, exact: true }}
                className={cn(buttonVariants({ variant: "ghost" }))}
                activeProps={{
                    className: cn(
                        buttonVariants({ variant: "default" }),
                        "hover:text-white"
                    ),
                }}
            >
                Tất cả
            </Link>
            <Link
                activeOptions={{ includeHash: true }}
                className={cn(buttonVariants({ variant: "ghost" }))}
                activeProps={{
                    className: cn(
                        buttonVariants({ variant: "default" }),
                        "hover:text-white"
                    ),
                }}
                to="/videos/$videoId/comments/member"
                params={{ videoId: videoId }}
            >
                Hội viên
            </Link>
            <Link
                activeOptions={{ includeHash: true }}
                className={cn(buttonVariants({ variant: "ghost" }))}
                activeProps={{
                    className: cn(
                        buttonVariants({ variant: "default" }),
                        "hover:text-white"
                    ),
                }}
                to="/videos/$videoId/comments/un-reply"
                params={{ videoId: videoId }}
            >
                Chưa phản hồi
            </Link>
        </div>
    );
};

export default CommentFilter;
