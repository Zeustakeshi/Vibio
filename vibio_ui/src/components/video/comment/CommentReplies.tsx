import { useState } from "react";
import { Button } from "../../ui/button";
import Replies from "./Replies";

type Props = { commentId: string; replyCount: number };

const CommentReplies = ({ commentId, replyCount }: Props) => {
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
            {showReplies && <Replies parentId={commentId}></Replies>}
        </div>
    );
};

export default CommentReplies;
