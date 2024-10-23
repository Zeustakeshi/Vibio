import { useInfiniteQuery } from "@tanstack/react-query";
import { useInView } from "react-intersection-observer";
import { getCommentReplies } from "../../../api/comment";

import { Comment } from "@/common/type/comment.type";
import { Button } from "@/components/ui/button";
import CommentItem from "./CommentItem";

type Props = { parentId: string; videoId: string };

const Replies = ({ parentId, videoId }: Props) => {
    const [ref, inView] = useInView();

    const { data, isLoading, fetchNextPage, hasNextPage, status } =
        useInfiniteQuery({
            queryKey: ["comment-replies", `${parentId}-replies`],
            queryFn: async (pages) => {
                return await getCommentReplies(
                    videoId,
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
                                    videoId={videoId}
                                    comment={comment}
                                    key={index}
                                    ref={ref}
                                    isReply
                                ></CommentItem>
                            );
                        return (
                            <CommentItem
                                videoId={videoId}
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
