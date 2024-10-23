import CommentFilter from "@/modules/video/comment/CommentFilter";
import { createFileRoute, Outlet } from "@tanstack/react-router";

export const Route = createFileRoute(
    "/videos/$videoId/_layout/comments/_layout"
)({
    component: VideoCommentLayout,
});

function VideoCommentLayout() {
    const { videoId } = Route.useParams();
    return (
        <div className="p-4">
            <h1 className="text-xl font-semibold">Bình luận về video</h1>
            <CommentFilter videoId={videoId}></CommentFilter>
            <div className="w-full">
                <Outlet></Outlet>
            </div>
        </div>
    );
}
