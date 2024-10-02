import CommentInput from "./CommentInput";
import CommentList from "./CommentList";

type Props = {};

const Comment = (props: Props) => {
    return (
        <div className="">
            <h4 className="text-xl font-semibold my-2">224 Bình luận</h4>
            <CommentInput></CommentInput>
            <CommentList></CommentList>
        </div>
    );
};

export default Comment;
