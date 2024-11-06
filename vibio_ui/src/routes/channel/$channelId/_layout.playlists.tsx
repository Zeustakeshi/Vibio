import { useInfiniteQuery } from "@tanstack/react-query";
import { createFileRoute, useNavigate } from "@tanstack/react-router";
import { useEffect } from "react";
import { useInView } from "react-intersection-observer";
import { getAllPublicPlaylistByChannelId } from "../../../api/playlist";
import { Playlist } from "../../../common/type/playlist";
import PlaylistItem from "../../../components/playlist/PlaylistItem";

export const Route = createFileRoute("/channel/$channelId/_layout/playlists")({
    component: ChannelPlaylists,
});

function ChannelPlaylists() {
    const navigation = useNavigate();
    const [ref, inView] = useInView();
    const { channelId } = Route.useParams();

    const { data, fetchNextPage, isFetchingNextPage, hasNextPage, status } =
        useInfiniteQuery({
            queryKey: ["channel-playlists", channelId],
            queryFn: async (pages) =>
                await getAllPublicPlaylistByChannelId(
                    channelId,
                    pages.pageParam ?? 0
                ),
            getNextPageParam: (lastPage: any) => {
                if (lastPage?.last) return undefined;
                return lastPage?.pageNumber + 1;
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
        <div className="grid gap-4 grid-cols-[repeat(auto-fill,_minmax(250px,_1fr))] my-5">
            {data &&
                data?.pages
                    .flatMap(({ content }: any) => content ?? [])
                    .map((playlist: Playlist, index, content) => {
                        const lastIndex = content.length - 1;
                        if (index === Math.ceil(lastIndex * 0.8))
                            return (
                                <PlaylistItem
                                    channelId={channelId}
                                    playlist={playlist}
                                    ref={ref}
                                    key={index}
                                ></PlaylistItem>
                            );
                        return (
                            <PlaylistItem
                                channelId={channelId}
                                playlist={playlist}
                                key={index}
                            ></PlaylistItem>
                        );
                    })}

            {isFetchingNextPage && <p>đang tải....</p>}

            {!hasNextPage && status !== "pending" && <p>Hết rồi</p>}
        </div>
    );
}
