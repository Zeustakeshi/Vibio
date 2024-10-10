import { useInfiniteQuery } from "@tanstack/react-query";
import { useEffect } from "react";
import { useInView } from "react-intersection-observer";
import { getFeed, getGuestFeed } from "../../api/video";
import { Video } from "../../common/type/video";
import { useAuth } from "../../context/AuthContext";
import FeedItem from "./FeedItem";

type Props = {};

const Feeds = ({}: Props) => {
    const { isAuthenticated } = useAuth();
    const [ref, inView] = useInView();

    const { data, fetchNextPage, isFetchingNextPage, hasNextPage, status } =
        useInfiniteQuery({
            queryKey: ["feeds"],
            queryFn: async (pages) => {
                if (isAuthenticated) return await getFeed(pages);
                else return await getGuestFeed(pages);
            },
            getNextPageParam: (lastPage: any, allPages: any[]) => {
                if (lastPage.last) return undefined;
                return allPages.length + 1;
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
        <div className="grid gap-4 grid-cols-[repeat(auto-fill,_minmax(250px,_1fr))]">
            {data &&
                data?.pages
                    .flatMap(({ content }: any) => content ?? [])
                    .map((video: Video, index, content) => {
                        const lastIndex = content.length - 1;
                        if (index === Math.ceil(lastIndex * 0.8))
                            return (
                                <FeedItem
                                    ref={ref}
                                    key={index}
                                    video={video}
                                ></FeedItem>
                            );
                        return <FeedItem key={index} video={video}></FeedItem>;
                    })}
            {isFetchingNextPage && <p>đang tải....</p>}

            {!hasNextPage && status !== "pending" && <p>Hết rồi</p>}
        </div>
    );
};

export default Feeds;
