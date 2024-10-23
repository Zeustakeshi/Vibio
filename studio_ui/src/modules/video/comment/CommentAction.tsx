import { reactionComment, unReactionComment } from "@/api/comment";
import { ReactionType } from "@/common/enum";
import { Comment } from "@/common/type/comment.type";
import { Button } from "@/components/ui/button";
import { useMutation } from "@tanstack/react-query";
import { useState } from "react";
import { FaRegHeart } from "react-icons/fa6";
import { toast } from "react-toastify";
import { cn } from "../../../lib/utils";
import CommentInput from "./CommentInput";

type Props = {
    comment: Comment;
    videoId: string;
};

const CommentAction = ({ comment, videoId }: Props) => {
    const [isReply, setIsReply] = useState<boolean>(false);
    const [reaction, setReaction] = useState<{
        loved: boolean;
    }>({
        loved: comment.lovedByChannel,
    });

    const reactionMutation = useMutation({
        mutationKey: ["comment-reaction", `comment-reaction-${comment.id}`],
        mutationFn: (reactionType: ReactionType) =>
            reactionComment(videoId, comment.id, reactionType),
    });

    const unReactionMutation = useMutation({
        mutationKey: [
            "comment-un-reaction",
            `comment-un-reaction-${comment.id}`,
        ],
        mutationFn: (reactionType: ReactionType) =>
            unReactionComment(videoId, comment.id, reactionType),
    });

    const handleReactionComment = async (reactionType: ReactionType) => {
        try {
            await reactionMutation.mutateAsync(reactionType);
            if (reactionType === ReactionType.LOVE) {
                setReaction({
                    loved: true,
                });
            } else {
                setReaction({
                    loved: true,
                });
            }
        } catch (error) {
            console.log(error);
            toast.error("Tương tác bình luận thất bại");
        }
    };

    const handleUnReactionComment = async (reactionType: ReactionType) => {
        try {
            await unReactionMutation.mutateAsync(reactionType);

            setReaction({
                loved: false,
            });
        } catch (error) {
            console.log(error);
            toast.error("Hủy tương tác bình luận thất bại");
        }
    };

    return (
        <div className="my-2 w-full">
            <div className="flex justify-start items-center w-full">
                <Button
                    onClick={() =>
                        reaction.loved
                            ? handleUnReactionComment(ReactionType.LOVE)
                            : handleReactionComment(ReactionType.LOVE)
                    }
                    disabled={reactionMutation.isPending}
                    size="sm"
                    variant="ghost"
                >
                    <FaRegHeart
                        className={cn({
                            "text-primary": reaction.loved,
                        })}
                        size={18}
                    />
                </Button>

                <Button
                    onClick={() => setIsReply((prev) => !prev)}
                    size="sm"
                    variant="ghost"
                >
                    Phản hồi
                </Button>
            </div>
            {isReply && (
                <div>
                    <CommentInput
                        videoId={videoId}
                        parentId={comment.id}
                        autoFocus
                        onCancelComment={() => setIsReply(false)}
                    ></CommentInput>
                </div>
            )}
        </div>
    );
};

export default CommentAction;
