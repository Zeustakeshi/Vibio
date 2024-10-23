import { Comment } from "@/common/type/comment.type";
import { Avatar, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { cn } from "@/lib/utils";
import moment from "moment";
import { forwardRef, useState } from "react";
import CommentAction from "./CommentAction";
import CommentReplies from "./CommentReplies";

type Props = {
    videoId: string;
    comment: Comment;
    isReply?: boolean;
};

const CommentItem = ({ comment, isReply, videoId }: Props, ref: any) => {
    const [isCollapse, setCollapse] = useState<boolean>(true);

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
                    <span className="font-semibold">
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
                <CommentAction
                    videoId={videoId}
                    comment={comment}
                ></CommentAction>
                {!isReply && comment.replyCount > 0 && (
                    <CommentReplies
                        videoId={videoId}
                        commentId={comment.id}
                        replyCount={comment.replyCount}
                    ></CommentReplies>
                )}
            </div>
        </div>
    );
};

export default forwardRef(CommentItem);
