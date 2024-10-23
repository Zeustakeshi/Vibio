import { getAllCommentByVideoId } from "@/api/comment";
import { Comment, CommentFilterType } from "@/common/type/comment.type";
import { useInfiniteQuery } from "@tanstack/react-query";
import CommentItem from "./CommentItem";

type Props = {
    type: CommentFilterType;
    videoId: string;
};

const CommentList = ({ type, videoId }: Props) => {
    const { data, fetchNextPage, isFetchingNextPage, hasNextPage, status } =
        useInfiniteQuery({
            queryKey: [`comments`, videoId],
            queryFn: async (pages) => {
                return await getAllCommentByVideoId(videoId, pages.pageParam);
            },
            getNextPageParam: (lastPage: any) => {
                if (lastPage.last) return undefined;
                return lastPage.pageNumber + 1;
            },
            initialPageParam: 0,
            refetchOnWindowFocus: false,
        });

    return (
        <div className="space-y-3 max-h-[72svh] overflow-y-scroll custom-scroll py-4">
            {data &&
                data?.pages
                    .flatMap(({ content }: any) => content ?? [])
                    .map((comment: Comment, index, content) => {
                        const lastIndex = content.length - 1;
                        if (index === Math.ceil(lastIndex * 0.8))
                            return (
                                <CommentItem
                                    videoId={videoId}
                                    key={index}
                                    comment={comment}
                                ></CommentItem>
                            );
                        return (
                            <CommentItem
                                videoId={videoId}
                                key={index}
                                comment={comment}
                            ></CommentItem>
                        );
                    })}
            {isFetchingNextPage && <p>đang tải....</p>}

            {!hasNextPage && status !== "pending" && <p>Hết rồi</p>}
        </div>
    );
};

export default CommentList;
