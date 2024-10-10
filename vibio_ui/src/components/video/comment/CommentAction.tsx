import { useState } from "react";
import { BiDislike, BiLike } from "react-icons/bi";
import { Button } from "../../ui/button";
import CommentInput from "./CommentInput";

type Props = {
    commentId: string;
};

const CommentAction = ({ commentId }: Props) => {
    const [isReply, setIsReply] = useState<boolean>(false);

    return (
        <div className="my-2 w-full">
            <div className="flex justify-start items-center w-full">
                <Button size="sm" variant="ghost">
                    <BiLike size={18} />
                </Button>
                <Button size="sm" variant="ghost">
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
                        parentId={commentId}
                        autoFocus
                        onCancelComment={() => setIsReply(false)}
                    ></CommentInput>
                </div>
            )}
        </div>
    );
};

export default CommentAction;
