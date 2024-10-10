import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { HiOutlineEmojiHappy } from "react-icons/hi";
import { z } from "zod";
import { createComment } from "../../../api/comment";
import { Avatar, AvatarImage } from "../../../components/ui/avatar";
import { Button } from "../../../components/ui/button";
import { useAuth } from "../../../context/AuthContext";
import { toast } from "../../../hooks/use-toast";
import useClickOutSide from "../../../hooks/useClickOutSide";
import { useWatchVideo } from "../../../routes/watch/$videoId";
import { newCommentSchema } from "../../../schema/comment.schema";
import { Form, FormField, FormItem, FormMessage } from "../../ui/form";
import TextArea from "../../ui/text-area";

type Props = {
    parentId?: string;
    onCancelComment?: () => void;
    autoFocus?: boolean;
};

const CommentInput = ({
    parentId,
    onCancelComment,
    autoFocus = false,
}: Props) => {
    const [showAction, setShowAction] = useState<boolean>(autoFocus);
    const { user } = useAuth();
    const form = useForm<z.infer<typeof newCommentSchema>>({
        resolver: zodResolver(newCommentSchema),
        defaultValues: {
            parentId,
        },
    });

    const { video } = useWatchVideo();

    const commentMutation = useMutation({
        mutationKey: ["new-comment"],
        mutationFn: (data: z.infer<typeof newCommentSchema>) =>
            createComment(video.id, data),
    });

    const { nodeRef: inputContainerRef } = useClickOutSide(() => {
        setShowAction(false);
    });

    const onSubmit = async (value: z.infer<typeof newCommentSchema>) => {
        try {
            await commentMutation.mutateAsync(value);
            form.setValue("content", "");
        } catch (error: any) {
            toast({ title: "Bình luận thất bại", description: error });
        }
    };

    if (!user) return <></>;

    return (
        <Form {...form}>
            <div className="flex justify-start items-start gap-4 my-2 transition-all">
                <Avatar>
                    <AvatarImage src={user.avatar}></AvatarImage>
                </Avatar>
                <div className="flex-1" ref={inputContainerRef}>
                    <FormField
                        control={form.control}
                        name="content"
                        render={({ field }) => (
                            <FormItem>
                                <TextArea
                                    autoFocus
                                    onFocus={() => setShowAction(true)}
                                    className="max-h-[100px] px-3 py-2 "
                                    placeholder="Viết bình luận của bạn ..."
                                    {...field}
                                ></TextArea>
                                <FormMessage></FormMessage>
                            </FormItem>
                        )}
                    />

                    {(showAction || autoFocus) && (
                        <div className="flex justify-between items-center gap-2 my-1 transition-all">
                            <Button variant="ghost" size="sm">
                                <HiOutlineEmojiHappy size={20} />
                            </Button>
                            <div className="flex justify-end items-center gap-2">
                                <Button
                                    disabled={commentMutation.isPending}
                                    onClick={() => {
                                        setShowAction(false);
                                        onCancelComment?.();
                                    }}
                                    type="button"
                                    variant="secondary"
                                >
                                    Hủy
                                </Button>
                                <Button
                                    onClick={form.handleSubmit(onSubmit)}
                                    disabled={commentMutation.isPending}
                                    type="submit"
                                >
                                    Bình luận
                                </Button>
                            </div>
                        </div>
                    )}
                </div>
            </div>
        </Form>
    );
};

export default CommentInput;
