import moment from "moment";
import { forwardRef, useState } from "react";
import { Comment } from "../../../common/type/comment";
import { useAuth } from "../../../context/AuthContext";
import { cn } from "../../../lib/utils";
import { useWatchVideo } from "../../../routes/watch/$videoId";
import { Avatar, AvatarImage } from "../../ui/avatar";
import { Button } from "../../ui/button";
import CommentAction from "./CommentAction";
import CommentReplies from "./CommentReplies";

type Props = {
    comment: Comment;
    isReply?: boolean;
};

const CommentItem = ({ comment, isReply }: Props, ref: any) => {
    const { video, channel } = useWatchVideo();
    const [isCollapse, setCollapse] = useState<boolean>(true);
    const { user } = useAuth();

    return (
        <div
            ref={ref}
            className="my-4 flex justify-start items-start gap-2 w-full"
        >
            <Avatar
                className={cn({
                    "size-[24px]": isReply,
                })}
            >
                <AvatarImage src={comment.owner.avatar}></AvatarImage>
            </Avatar>
            <div className="w-full">
                <div className="flex justify-start items-center gap-2 text-xs text-muted-foreground">
                    <span
                        className={cn("font-semibold ", {
                            "px-2 py-1 rounded-md bg-primary text-white font-semibold":
                                channel?.accountId === comment.owner.id,
                        })}
                    >
                        {comment.owner.username}
                    </span>
                    <span>{moment(comment.updatedAt).toNow()}</span>
                </div>
                <p
                    className={cn("text-sm mt-2", {
                        "line-clamp-3": isCollapse,
                    })}
                >
                    {comment.content}
                </p>
                {comment.content.length > 1000 && (
                    <Button
                        onClick={() => setCollapse((collapse) => !collapse)}
                        variant="link"
                        size="sm"
                        className="px-0"
                    >
                        {isCollapse ? "Đọc thêm" : "Ẩn bớt"}
                    </Button>
                )}
                <CommentAction comment={comment}></CommentAction>
                {!isReply && comment.replyCount > 0 && (
                    <CommentReplies
                        commentId={comment.id}
                        replyCount={comment.replyCount}
                    ></CommentReplies>
                )}
            </div>
        </div>
    );
};

export default forwardRef(CommentItem);
