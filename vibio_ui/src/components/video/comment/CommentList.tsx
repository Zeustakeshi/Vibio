import { useInfiniteQuery } from "@tanstack/react-query";
import { useEffect } from "react";
import { useInView } from "react-intersection-observer";
import {
    getCommentByVideoId,
    getCommentGuestByVideoId,
} from "../../../api/comment";
import { Comment } from "../../../common/type/comment";
import { useAuth } from "../../../context/AuthContext";
import { useWatchVideo } from "../../../routes/watch/$videoId";
import CommentItem from "./CommentItem";

type Props = {};

const CommentList = (props: Props) => {
    const { video } = useWatchVideo();
    const { isAuthenticated } = useAuth();
    const [ref, inView] = useInView();

    const { data, fetchNextPage, isFetchingNextPage, hasNextPage, status } =
        useInfiniteQuery({
            queryKey: ["comments", "video-comment", video.id],
            queryFn: async (pages) => {
                if (isAuthenticated)
                    return await getCommentByVideoId(video.id, pages.pageParam);
                else
                    return await getCommentGuestByVideoId(
                        video.id,
                        pages.pageParam
                    );
            },
            getNextPageParam: (lastPage: any, allPages: any[]) => {
                if (lastPage.last) return undefined;
                return allPages.length + 1;
            },
            initialPageParam: 0,
            refetchOnWindowFocus: false,
        });

    useEffect(() => {
        if (inView) fetchNextPage();
    }, [inView]);

    return (
        <div className="my-3 max-w-[800px]">
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
                                ></CommentItem>
                            );
                        return (
                            <CommentItem
                                comment={comment}
                                key={index}
                            ></CommentItem>
                        );
                    })}
        </div>
    );
};

export default CommentList;
