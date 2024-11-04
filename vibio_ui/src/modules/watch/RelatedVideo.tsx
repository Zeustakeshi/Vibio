import { useInfiniteQuery } from "@tanstack/react-query";
import { useEffect } from "react";
import { useInView } from "react-intersection-observer";
import { searchVideo } from "../../api/search";
import { SearchVideo } from "../../common/type/video";
import { useWatchVideo } from "../../routes/watch/$videoId";
import RelatedVideoItem from "./RelatedVideoItem";

type Props = {};

const RelatedVideo = (props: Props) => {
    const { video, channel } = useWatchVideo();
    const [ref, inView] = useInView();

    const {
        data: searchResults,
        fetchNextPage,
        isFetchingNextPage,
        hasNextPage,
        status,
    } = useInfiniteQuery({
        queryKey: ["related-video", video.id],
        queryFn: async (pages) => {
            return await searchVideo(
                video.title + " " + channel?.name,
                pages.pageParam
            );
        },
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

    return (
        <div>
            <h3 className="text-xl font-semibold mb-4">Đề xuất cho bạn</h3>
            <div className="flex flex-col gap-2 justify-start items-center">
                {searchResults &&
                    searchResults?.pages
                        .flatMap(({ content }: any) => content ?? [])
                        .filter((v) => v.id !== video.id)
                        .map((video: SearchVideo, index, content) => {
                            const lastIndex = content.length - 1;
                            if (index === Math.ceil(lastIndex * 0.8))
                                return (
                                    <RelatedVideoItem
                                        ref={ref}
                                        key={index}
                                        video={video}
                                    ></RelatedVideoItem>
                                );
                            return (
                                <RelatedVideoItem
                                    key={index}
                                    video={video}
                                ></RelatedVideoItem>
                            );
                        })}
                {isFetchingNextPage && <p>đang tải....</p>}

                {!hasNextPage && <p>Hết rồi</p>}
            </div>
        </div>
    );
};

export default RelatedVideo;
