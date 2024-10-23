import { Button } from "@/components/ui/button";
import { useState } from "react";
import Replies from "./Replies";

type Props = { videoId: string; commentId: string; replyCount: number };

const CommentReplies = ({ videoId, commentId, replyCount }: Props) => {
    const [showReplies, setShowReplies] = useState<boolean>(false);
    return (
        <div>
            <Button
                onClick={() => setShowReplies((prev) => !prev)}
                variant="ghost"
                size="sm"
            >
                {showReplies ? "Ẩn" : "Xem thêm"} {replyCount} Phản hồi
            </Button>
            {showReplies && (
                <Replies videoId={videoId} parentId={commentId}></Replies>
            )}
        </div>
    );
};

export default CommentReplies;
