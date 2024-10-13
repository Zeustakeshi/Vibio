import { useInfiniteQuery } from "@tanstack/react-query";
import { useInView } from "react-intersection-observer";
import { getCommentReplies } from "../../../api/comment";
import { Comment } from "../../../common/type/comment";
import { useAuth } from "../../../context/AuthContext";
import { useWatchVideo } from "../../../routes/watch/$videoId";
import { Button } from "../../ui/button";
import CommentItem from "./CommentItem";

type Props = { parentId: string };

const commentTest: Comment = {
    content: "hello world",
    id: "1000",
    likeCount: 10,
    replyCount: 2,
    owner: {
        avatar: "",
        id: "",
        username: "",
    },
    updated: false,
    updatedAt: "",
};

const Replies = ({ parentId }: Props) => {
    const { video } = useWatchVideo();

    const { isAuthenticated } = useAuth();
    const [ref, inView] = useInView();

    const { data, isLoading, fetchNextPage, hasNextPage, status } =
        useInfiniteQuery({
            queryKey: ["comment-replies", `${parentId}-replies`],
            queryFn: async (pages) => {
                if (isAuthenticated)
                    return await getCommentReplies(
                        video.id,
                        pages.pageParam,
                        parentId
                    );
                else
                    return await getCommentReplies(
                        video.id,
                        pages.pageParam,
                        parentId
                    );
            },
            getNextPageParam: (lastPage: any, allPages: any[]) => {
                if (lastPage.last) return undefined;
                return allPages.length + 1;
            },
            initialPageParam: 0,
            refetchOnWindowFocus: false,
            staleTime: 100000,
        });

    return (
        <div>
            {data &&
                data?.pages
                    .flatMap(({ content }: any) => content ?? [])
                    .map((comment: Comment, index, content) => {
                        const lastIndex = content.length - 1;
                        if (index === Math.ceil(lastIndex * 0.8))
                            return (
                                <CommentItem
                                    comment={comment}
                                    key={index}
                                    ref={ref}
                                    isReply
                                ></CommentItem>
                            );
                        return (
                            <CommentItem
                                comment={comment}
                                key={index}
                                isReply
                            ></CommentItem>
                        );
                    })}
            {hasNextPage && status !== "pending" && <Button>Xem thêm</Button>}
        </div>
    );
};

export default Replies;
