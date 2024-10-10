import { useWatchVideo } from "../../../routes/watch/$videoId";
import CommentInput from "./CommentInput";
import CommentList from "./CommentList";

type Props = {};

const Comment = ({}: Props) => {
    const { video } = useWatchVideo();
    return (
        <div className="">
            <h4 className="text-lg font-semibold my-2">
                {video?.commentCount ?? 0} Bình luận
            </h4>
            <CommentInput></CommentInput>
            <CommentList></CommentList>
        </div>
    );
};

export default Comment;
