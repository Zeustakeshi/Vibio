import CommentList from "@/modules/video/comment/CommentList";
import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute(
    "/videos/$videoId/_layout/comments/_layout/"
)({
    component: CommentPage,
});

function CommentPage() {
    const { videoId } = Route.useParams();

    return <CommentList type="ALL" videoId={videoId}></CommentList>;
}
