import { useInfiniteQuery, useQuery } from "@tanstack/react-query";
import { createFileRoute, useNavigate } from "@tanstack/react-router";
import { useEffect } from "react";
import { FaShare } from "react-icons/fa";
import { TbMessageReportFilled } from "react-icons/tb";
import { useInView } from "react-intersection-observer";
import {
    getAllVideoByPlaylistId,
    getAllVideoPublicByPlaylistId,
    getPlaylistById,
} from "../../api/playlist";
import { PlaylistVideo } from "../../common/type/playlist";
import { Button } from "../../components/ui/button";
import { useAuth } from "../../context/AuthContext";
import { convertVisibilityToLabel } from "../../lib/utils";
import PlaylistVideoItem from "../../modules/playlist/PlaylistVideoItem";

export const Route = createFileRoute("/playlists/$playlistId")({
    component: PlaylistDetailPage,
});

function PlaylistDetailPage() {
    const { playlistId } = Route.useParams();
    const { isAuthenticated } = useAuth();
    const [ref, inView] = useInView();
    const navigation = useNavigate();

    const { data: playlist } = useQuery({
        queryKey: ["playlist-detail", playlistId],
        queryFn: () => getPlaylistById(playlistId),
        refetchInterval: false,
        refetchIntervalInBackground: false,
    });

    const { data, fetchNextPage, isFetchingNextPage, hasNextPage, status } =
        useInfiniteQuery({
            queryKey: [`playlist-${playlistId}-videos`],
            queryFn: async (pages) => {
                if (isAuthenticated)
                    return await getAllVideoByPlaylistId(
                        playlistId,
                        pages.pageParam
                    );
                else
                    return await getAllVideoPublicByPlaylistId(
                        playlistId,
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
        <div className="p-4 flex items-start h-full w-full  gap-2">
            <div className="relative w-[340px]  h-full rounded-xl overflow-hidden p-5">
                <div className="abs-center z-[-1] w-full h-full bg-slate-900/80">
                    <img
                        className="w-full h-full  object-center blur-3xl"
                        src={playlist?.defaultThumbnail}
                        alt=""
                    />
                </div>
                <div className="rounded-xl overflow-hidden w-full h-[180px] bg-slate-300">
                    <img
                        className="w-full h-full object-cover"
                        src={playlist?.defaultThumbnail}
                        alt="playlist-thumbnail"
                    />
                </div>
                <div className="flex flex-col justify-start items-start text-white">
                    <h1 className="text-2xl font-semibold my-3">
                        {playlist?.name}
                    </h1>

                    <div className="flex justify-start items-start gap-1 text-xs my-2">
                        <span>Danh sách phát</span> •{" "}
                        <span>{playlist?.videoCount ?? 0} video</span> •
                        {playlist?.visibility && (
                            <span>
                                {convertVisibilityToLabel(playlist?.visibility)}
                            </span>
                        )}
                    </div>
                    <div className="flex justify-start items-center gap-3 my-2 w-full">
                        <Button
                            onClick={() => {
                                const firstVideo = data?.pages.flatMap(
                                    ({ content }: any) => content ?? []
                                )?.[0];

                                if (!firstVideo) return;
                                navigation({
                                    to: "/watch/$videoId",
                                    params: { videoId: firstVideo.id },
                                    search: { list: playlist?.id ?? "" },
                                });
                            }}
                            variant="secondary"
                            className="flex-1"
                        >
                            Phát tất cả
                        </Button>
                        <div className="flex justify-end items-center gap-2 ">
                            <Button
                                variant="secondary"
                                size="icon"
                                className="!bg-slate-200/50 text-white"
                            >
                                <FaShare />
                            </Button>
                            <Button
                                variant="secondary"
                                className="!bg-slate-200/50 text-white"
                                size="icon"
                            >
                                <TbMessageReportFilled />
                            </Button>
                        </div>
                    </div>
                </div>
            </div>
            {/* video list */}
            <div className="flex-1  h-full overflow-y-scroll custom-scroll max-h-[85svh] space-y-2">
                {data &&
                    data?.pages
                        .flatMap(({ content }: any) => content ?? [])
                        .map((video: PlaylistVideo, index, content) => {
                            const lastIndex = content.length - 1;
                            if (index === Math.ceil(lastIndex * 0.8))
                                return (
                                    <PlaylistVideoItem
                                        ref={ref}
                                        key={index}
                                        video={video}
                                        playlistId={playlistId}
                                    ></PlaylistVideoItem>
                                );
                            return (
                                <PlaylistVideoItem
                                    key={index}
                                    video={video}
                                    playlistId={playlistId}
                                ></PlaylistVideoItem>
                            );
                        })}
                {isFetchingNextPage && <p>đang tải....</p>}
            </div>
        </div>
    );
}
