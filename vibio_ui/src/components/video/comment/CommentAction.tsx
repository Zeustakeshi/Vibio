import { useMutation } from "@tanstack/react-query";
import { useState } from "react";
import { BiDislike, BiLike } from "react-icons/bi";
import { reactionComment, unReactionComment } from "../../../api/comment";
import { ReactionType } from "../../../common/enum";
import { Comment } from "../../../common/type/comment";
import { useToast } from "../../../hooks/use-toast";
import { cn } from "../../../lib/utils";
import { useWatchVideo } from "../../../routes/watch/$videoId";
import { Button } from "../../ui/button";
import CommentInput from "./CommentInput";

type Props = {
    comment: Comment;
};

const CommentAction = ({ comment }: Props) => {
    const { video } = useWatchVideo();
    const [isReply, setIsReply] = useState<boolean>(false);
    const [reaction, setReaction] = useState<{
        liked: boolean;
        disliked: boolean;
    }>({
        liked: comment.liked,
        disliked: comment.disliked,
    });

    const [reactionCount, setReactionCount] = useState<{
        like: number;
        dislike: number;
    }>({
        like: comment.likeCount ?? 0,
        dislike: comment.dislikeCount ?? 0,
    });

    const reactionMutation = useMutation({
        mutationKey: ["comment-reaction", `comment-reaction-${comment.id}`],
        mutationFn: (reactionType: ReactionType) =>
            reactionComment(video.id, comment.id, reactionType),
    });

    const unReactionMutation = useMutation({
        mutationKey: [
            "comment-un-reaction",
            `comment-un-reaction-${comment.id}`,
        ],
        mutationFn: (reactionType: ReactionType) =>
            unReactionComment(video.id, comment.id, reactionType),
    });

    const { toast } = useToast();

    const handleReactionComment = async (reactionType: ReactionType) => {
        try {
            await reactionMutation.mutateAsync(reactionType);
            if (reactionType === ReactionType.LIKE) {
                setReactionCount({
                    like: reactionCount.like + 1,
                    dislike: reaction.disliked
                        ? reactionCount.dislike - 1
                        : reactionCount.dislike,
                });
                setReaction({
                    liked: true,
                    disliked: false,
                });
            } else {
                setReactionCount({
                    dislike: reactionCount.dislike + 1,
                    like: reaction.liked
                        ? reactionCount.like - 1
                        : reactionCount.like,
                });
                setReaction({
                    liked: false,
                    disliked: true,
                });
            }
        } catch (error) {
            toast({
                title: "Tương tác bình luận thất bại",
                description: error,
            });
        }
    };

    const handleUnReactionComment = async (reactionType: ReactionType) => {
        try {
            await unReactionMutation.mutateAsync(reactionType);

            setReaction({
                liked: false,
                disliked: false,
            });

            if (reactionType === ReactionType.LIKE) {
                setReactionCount({
                    ...reactionCount,
                    like: reactionCount.like - 1,
                });
            } else {
                setReactionCount({
                    ...reactionCount,
                    dislike: reactionCount.dislike - 1,
                });
            }
        } catch (error) {
            toast({
                title: "Hủy tương tác bình luận thất bại",
                description: error,
            });
        }
    };

    return (
        <div className="my-2 w-full">
            <div className="flex justify-start items-center w-full">
                <Button
                    onClick={() =>
                        reaction.liked
                            ? handleUnReactionComment(ReactionType.LIKE)
                            : handleReactionComment(ReactionType.LIKE)
                    }
                    disabled={reactionMutation.isPending}
                    size="sm"
                    variant="ghost"
                >
                    <span className="mx-3">{reactionCount.like}</span>
                    <BiLike
                        className={cn({
                            "text-primary": reaction.liked,
                        })}
                        size={18}
                    />
                </Button>
                <Button
                    disabled={reactionMutation.isPending}
                    size="sm"
                    className={cn({
                        "text-primary": reaction.disliked,
                    })}
                    variant="ghost"
                    onClick={() =>
                        reaction.disliked
                            ? handleUnReactionComment(ReactionType.DISLIKE)
                            : handleReactionComment(ReactionType.DISLIKE)
                    }
                >
                    <span className="mx-3">{reactionCount.dislike}</span>
                    <BiDislike size={18} />
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
