import { createComment } from "@/api/comment";
import { Form, FormField, FormItem, FormMessage } from "@/components/ui/form";
import TextArea from "@/components/ui/text-area";
import { useAuth } from "@/context/AuthContext";
import useClickOutSide from "@/hooks/useClickOutSide";
import { newCommentSchema } from "@/schema/comment.schema";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { HiOutlineEmojiHappy } from "react-icons/hi";
import { toast } from "react-toastify";
import { z } from "zod";
import { Avatar, AvatarImage } from "../../../components/ui/avatar";
import { Button } from "../../../components/ui/button";

type Props = {
    videoId: string;
    parentId?: string;
    onCancelComment?: () => void;
    autoFocus?: boolean;
};

const CommentInput = ({
    parentId,
    onCancelComment,
    autoFocus = false,
    videoId,
}: Props) => {
    const [showAction, setShowAction] = useState<boolean>(autoFocus);

    const { channel } = useAuth();

    const form = useForm<z.infer<typeof newCommentSchema>>({
        resolver: zodResolver(newCommentSchema),
        defaultValues: {
            parentId,
        },
    });

    const commentMutation = useMutation({
        mutationKey: ["new-comment"],
        mutationFn: (data: z.infer<typeof newCommentSchema>) =>
            createComment(videoId, data),
    });

    const { nodeRef: inputContainerRef } = useClickOutSide(() => {
        setShowAction(false);
    });

    const onSubmit = async (value: z.infer<typeof newCommentSchema>) => {
        try {
            await commentMutation.mutateAsync(value);
            form.setValue("content", "");
        } catch (error: any) {
            console.log(error);
            toast.error("Bình luận thất bại");
        }
    };

    return (
        <Form {...form}>
            <div className="flex justify-start items-start gap-4 my-2 transition-all">
                <Avatar>
                    <AvatarImage src={channel?.thumbnail}></AvatarImage>
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
