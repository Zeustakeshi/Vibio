import { useInfiniteQuery } from "@tanstack/react-query";
import { createFileRoute } from "@tanstack/react-router";
import { useEffect } from "react";
import { useInView } from "react-intersection-observer";
import { getAllVideoByChannelId } from "../../../api/video";
import { Video } from "../../../common/type/video";
import VideoItem from "../../../components/video/VideoItem";
import FeedLoading from "../../../modules/feed/FeedLoading";

export const Route = createFileRoute("/channel/$channelId/_layout/videos")({
    component: ChannelVideoPage,
});

function ChannelVideoPage() {
    const { channelId } = Route.useParams();
    const [ref, inView] = useInView();
    const {
        data,
        fetchNextPage,
        isFetchingNextPage,
        hasNextPage,
        status,
        isLoading,
    } = useInfiniteQuery({
        queryKey: ["channel-videos", channelId],
        queryFn: async (pages) =>
            getAllVideoByChannelId(channelId, pages.pageParam),
        getNextPageParam: (lastPage: any) => {
            if (lastPage.last) return undefined;
            return lastPage.pageNumber + 1;
        },
        initialPageParam: 0,
        refetchOnWindowFocus: false,
    });

    useEffect(() => {
        if (inView && hasNextPage) {
            fetchNextPage();
        }
    }, [inView]);

    console.log(JSON.stringify(data));

    if (isLoading) return <FeedLoading></FeedLoading>;

    return (
        <div className="grid gap-4 grid-cols-[repeat(auto-fill,_minmax(250px,_1fr))]">
            {data &&
                data?.pages
                    .flatMap(({ content }: any) => content ?? [])
                    .map((video: Video, index, content) => {
                        const lastIndex = content.length - 1;
                        if (index === Math.ceil(lastIndex * 0.8))
                            return (
                                <VideoItem
                                    ref={ref}
                                    key={index}
                                    video={video}
                                ></VideoItem>
                            );
                        return (
                            <VideoItem key={index} video={video}></VideoItem>
                        );
                    })}
            {isFetchingNextPage && <FeedLoading></FeedLoading>}

            {!hasNextPage && status !== "pending" && <p>Hết rồi</p>}
        </div>
    );
}
