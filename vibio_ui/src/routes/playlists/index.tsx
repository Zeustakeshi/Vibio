import { useInfiniteQuery } from "@tanstack/react-query";
import { createFileRoute, useNavigate } from "@tanstack/react-router";
import { useEffect } from "react";
import { useInView } from "react-intersection-observer";
import { getAllPlaylistByChannelId } from "../../api/playlist";
import { Playlist } from "../../common/type/playlist";
import NewPlaylistDialog from "../../components/playlist/NewPlaylistDialog";
import PlaylistItem from "../../components/playlist/PlaylistItem";
import { Button } from "../../components/ui/button";
import { useAuth } from "../../context/AuthContext";

export const Route = createFileRoute("/playlists/")({
    component: PlaylistPage,
});

function PlaylistPage() {
    const [ref, inView] = useInView();

    const { isAuthenticated, authLoading, user } = useAuth();

    const navigation = useNavigate();

    useEffect(() => {
        if (authLoading) return;

        if (!isAuthenticated || !user?.channel.id) {
            navigation({
                to: "/auth/login",
            });
            return;
        }
    }, [isAuthenticated, authLoading]);

    const {
        data,
        fetchNextPage,
        isFetchingNextPage,
        hasNextPage,
        status,
        refetch,
    } = useInfiniteQuery({
        queryKey: ["user-playlists"],
        queryFn: async (pages) =>
            await getAllPlaylistByChannelId(pages.pageParam ?? 0),
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
        <div className="p-4">
            <div className="flex justify-between items-center">
                <h1 className="text-3xl font-semibold">Danh sách phát</h1>
                <NewPlaylistDialog onSaveSuccess={() => refetch()}>
                    <Button>Thêm danh sách</Button>
                </NewPlaylistDialog>
            </div>
            <div className="grid gap-4 grid-cols-[repeat(auto-fill,_minmax(250px,_1fr))] my-5">
                {data &&
                    data?.pages
                        .flatMap(({ content }: any) => content ?? [])
                        .map((playlist: Playlist, index, content) => {
                            const lastIndex = content.length - 1;
                            if (index === Math.ceil(lastIndex * 0.8))
                                return (
                                    <PlaylistItem
                                        playlist={playlist}
                                        ref={ref}
                                        key={index}
                                    ></PlaylistItem>
                                );
                            return (
                                <PlaylistItem
                                    playlist={playlist}
                                    key={index}
                                ></PlaylistItem>
                            );
                        })}

                {isFetchingNextPage && <p>đang tải....</p>}

                {!hasNextPage && status !== "pending" && <p>Hết rồi</p>}
            </div>
        </div>
    );
}
