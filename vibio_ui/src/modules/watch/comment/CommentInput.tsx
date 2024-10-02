import { useState } from "react";
import { HiOutlineEmojiHappy } from "react-icons/hi";
import { Avatar, AvatarImage } from "../../../components/ui/avatar";
import { Button } from "../../../components/ui/button";
import useClickOutSide from "../../../hooks/useClickoutSide";

type Props = {};

const CommentInput = (props: Props) => {
    const [comment, setComment] = useState<string>("");
    const [showAction, setShowAction] = useState<boolean>(false);

    const { nodeRef: inputContainerRef } = useClickOutSide(() => {
        setShowAction(false);
    });

    return (
        <form className="flex justify-start items-start gap-4 my-2 transition-all">
            <Avatar>
                <AvatarImage src="https://yt3.ggpht.com/ytc/AIdro_mKzklyPPhghBJQH5H3HpZ108YcE618DBRLAvRUD1AjKNw=s48-c-k-c0x00ffffff-no-rj"></AvatarImage>
            </Avatar>
            <div className="flex-1" ref={inputContainerRef}>
                <div>
                    <input
                        onFocus={() => setShowAction(true)}
                        value={comment}
                        onChange={(e) => setComment(e.target.value)}
                        className="transition-all border-b border-b-slate-200 py-1 w-full outline-none focus:border-b-primary"
                        placeholder="Viết bình luận"
                    ></input>
                </div>
                {showAction && (
                    <div className="flex justify-between items-center gap-2 my-2 transition-all">
                        <Button variant="ghost" size="sm">
                            <HiOutlineEmojiHappy size={20} />
                        </Button>
                        <div className="flex justify-end items-center gap-2">
                            <Button
                                onClick={() => setShowAction(false)}
                                type="button"
                                variant="secondary"
                            >
                                Hủy
                            </Button>
                            <Button
                                disabled={comment.trim().length === 0}
                                type="submit"
                            >
                                Bình luận
                            </Button>
                        </div>
                    </div>
                )}
            </div>
        </form>
    );
};

export default CommentInput;
