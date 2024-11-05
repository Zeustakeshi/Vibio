import { useInfiniteQuery } from "@tanstack/react-query";
import { useEffect } from "react";
import { useInView } from "react-intersection-observer";
import { searchVideo } from "../../api/search";
import { SearchVideo } from "../../common/type/video";
import RandomImage from "../../components/ui/randomImage";
import SearchVideoItem from "./SearchVideoItem";

type Props = {
    query?: string;
};

const SearchResult = ({ query }: Props) => {
    const [ref, inView] = useInView();

    const {
        data: searchResults,
        fetchNextPage,
        isFetchingNextPage,
        hasNextPage,
        status,
    } = useInfiniteQuery({
        queryKey: [`search-${query}`],
        queryFn: async (pages) => {
            if (!query) return null;
            return await searchVideo(query, pages.pageParam);
        },
        getNextPageParam: (lastPage: any) => {
            if (lastPage.last) return undefined;
            return lastPage.pageNumber + 1;
        },
        initialPageParam: 0,
        refetchOnWindowFocus: false,
        enabled: !!query,
    });

    useEffect(() => {
        if (inView && hasNextPage) {
            fetchNextPage();
        }
    }, [inView]);

    if (!query) {
        return (
            <div className="w-full h-full flex flex-col justify-center items-center">
                <RandomImage className="size-[400px]"></RandomImage>
                <p className="text-muted-foreground">
                    Nhập từ khóa để tìm kiếm video bạn muốn xem
                </p>
            </div>
        );
    }

    if (status === "pending") {
        return (
            <div className="w-full h-full flex flex-col justify-center items-center">
                <RandomImage className="size-[400px]"></RandomImage>
                <p className="text-muted-foreground ">
                    Đang tìm kiếm từ khóa{" "}
                    <span className="font-semibold text-primary">{query}</span>
                </p>
            </div>
        );
    }

    return (
        <div className="my-3 flex flex-col justify-start items-start gap-5">
            {searchResults &&
                searchResults?.pages
                    .flatMap(({ content }: any) => content ?? [])
                    .map((video: SearchVideo, index, content) => {
                        const lastIndex = content.length - 1;
                        if (index === Math.ceil(lastIndex * 0.8))
                            return (
                                <SearchVideoItem
                                    ref={ref}
                                    key={index}
                                    video={video}
                                ></SearchVideoItem>
                            );
                        return (
                            <SearchVideoItem
                                key={index}
                                video={video}
                            ></SearchVideoItem>
                        );
                    })}
            {isFetchingNextPage && <p>đang tải....</p>}

            {!hasNextPage && <p>Hết rồi</p>}
        </div>
    );
};

export default SearchResult;
