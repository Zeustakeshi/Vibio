import { useMutation } from "@tanstack/react-query";
import { useState } from "react";
import { BiDislike, BiLike } from "react-icons/bi";
import { IoMdMore } from "react-icons/io";
import { LuDownload } from "react-icons/lu";
import { TbShare3 } from "react-icons/tb";
import { reactionVideo, unReactionVideo } from "../../api/video";
import { ReactionType } from "../../common/enum";
import { Button } from "../../components/ui/button";
import { useToast } from "../../hooks/use-toast";
import { cn } from "../../lib/utils";
import { useWatchVideo } from "../../routes/watch/$videoId";
type Props = {};

const VideoAction = ({}: Props) => {
    const { video } = useWatchVideo();

    const [reaction, setReaction] = useState<{
        liked: boolean;
        disliked: boolean;
    }>({
        liked: video.liked,
        disliked: video.disliked,
    });
    const [likeCount, setLikeCount] = useState<number>(video.likeCount);

    const reactionVideoMutation = useMutation({
        mutationKey: [
            "reaction",
            "reaction-video",
            `reaction-video-${video.id}`,
        ],
        mutationFn: (reactionType: ReactionType) =>
            reactionVideo(video.id, reactionType),
    });

    const unReactionVideoMutation = useMutation({
        mutationKey: [
            "un-reaction",
            "un-reaction-video",
            `un-reaction-video-${video.id}`,
        ],
        mutationFn: (reactionType: ReactionType) =>
            unReactionVideo(video.id, reactionType),
    });

    const { toast } = useToast();

    const handleReactionVideo = async (reactionType: ReactionType) => {
        try {
            await reactionVideoMutation.mutateAsync(reactionType);

            if (reactionType === ReactionType.LIKE) {
                setLikeCount((likeCount) => likeCount + 1);
                setReaction({
                    liked: true,
                    disliked: false,
                });
            } else {
                if (reaction.liked) setLikeCount((likeCount) => likeCount - 1);
                setReaction({
                    liked: false,
                    disliked: true,
                });
            }
        } catch (error) {
            toast({
                title: "Tương tác video thất bại",
                description: error,
            });
        }
    };

    const handleUnReactionVideo = async (reactionType: ReactionType) => {
        try {
            await unReactionVideoMutation.mutateAsync(reactionType);
            setLikeCount((likeCount) => likeCount - 1);
            setReaction({
                liked: false,
                disliked: false,
            });
        } catch (error) {
            toast({
                title: "Hủy tương tác video thất bại",
                description: error,
            });
        }
    };

    return (
        <div className="flex justify-end items-center gap-2">
            <div className="rounded-full overflow-hidden flex">
                <Button
                    disabled={
                        reactionVideoMutation.isPending ||
                        unReactionVideoMutation.isPending
                    }
                    onClick={() =>
                        reaction.liked
                            ? handleUnReactionVideo(ReactionType.LIKE)
                            : handleReactionVideo(ReactionType.LIKE)
                    }
                    variant={reaction.liked ? "default" : "secondary"}
                    className={cn(
                        "!rounded-tr-none !rounded-br-none gap-2 text-slate-800",
                        {
                            "text-white": reaction.liked,
                        }
                    )}
                >
                    <BiLike size={20}></BiLike>
                    <span>{likeCount}</span>
                </Button>
                <Button
                    disabled={
                        reactionVideoMutation.isPending ||
                        unReactionVideoMutation.isPending
                    }
                    variant={reaction.disliked ? "default" : "secondary"}
                    onClick={() =>
                        reaction.disliked
                            ? handleUnReactionVideo(ReactionType.DISLIKE)
                            : handleReactionVideo(ReactionType.DISLIKE)
                    }
                    className={cn(
                        "!rounded-tr-none !rounded-br-none gap-2 text-slate-800",
                        {
                            "text-white": reaction.disliked,
                        }
                    )}
                >
                    <BiDislike size={20} />
                </Button>
            </div>

            <Button
                variant="secondary"
                className="rounded-full gap-2 text-slate-800"
            >
                <TbShare3 size={20} />
                <span>Chia sẻ</span>
            </Button>

            <Button
                variant="secondary"
                className="rounded-full gap-2 text-slate-800"
            >
                <LuDownload size={20} />
                <span>Lưu</span>
            </Button>

            <Button
                variant="secondary"
                className="rounded-full gap-2 text-slate-800"
            >
                <IoMdMore size={20} />
            </Button>
        </div>
    );
};

export default VideoAction;
