import moment from "moment";
import { forwardRef, useState } from "react";
import { Comment } from "../../../common/type/comment";
import { cn } from "../../../lib/utils";
import { Avatar, AvatarImage } from "../../ui/avatar";
import { Button } from "../../ui/button";
import CommentAction from "./CommentAction";
import CommentReplies from "./CommentReplies";

type Props = {
    comment: Comment;
    isReply?: boolean;
};

const CommentItem = ({ comment, isReply }: Props, ref: any) => {
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
                <AvatarImage src="https://th.bing.com/th/id/R.69b6c7c1419fedc585d4aac2958c5ae4?rik=Ti4lNMU9Co54jg&pid=ImgRaw&r=0"></AvatarImage>
            </Avatar>
            <div className="w-full">
                <div className="flex justify-start items-center gap-2 text-xs text-muted-foreground">
                    <span className="font-semibold">Minh hiếu</span>
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
                <CommentAction commentId={comment.id}></CommentAction>
                {!isReply && (
                    <CommentReplies commentId={comment.id}></CommentReplies>
                )}
            </div>
        </div>
    );
};

export default forwardRef(CommentItem);
