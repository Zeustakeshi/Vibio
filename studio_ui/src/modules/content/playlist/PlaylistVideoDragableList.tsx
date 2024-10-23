type Props = {
    playlistId: string;
};

import {
    closestCenter,
    DndContext,
    MouseSensor,
    TouchSensor,
    useSensor,
    useSensors,
} from "@dnd-kit/core";
import { useInView } from "react-intersection-observer";

import {
    arrayMove,
    SortableContext,
    verticalListSortingStrategy,
} from "@dnd-kit/sortable";

import {
    getAllVideoByPlaylistId,
    updatePlaylistVideoOrder,
} from "@/api/playlist";
import { PlaylistVideo } from "@/common/type/playlist.type";
import { useInfiniteQuery, useMutation } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import PlaylistVideoDragableItem from "./PlaylistVideoDragableItem";

const PlaylistVideoDragableList = ({ playlistId }: Props) => {
    const [videos, setVideos] = useState<PlaylistVideo[]>([]);

    const [ref, inView] = useInView();

    const { data, fetchNextPage, isFetchingNextPage, hasNextPage, status } =
        useInfiniteQuery({
            queryKey: [`playlist-videos-${playlistId}`],
            queryFn: async (pages) =>
                getAllVideoByPlaylistId(playlistId, pages.pageParam),
            getNextPageParam: (lastPage: any) => {
                if (lastPage.last) return undefined;
                return lastPage.pageNumber + 1;
            },
            initialPageParam: 0,
            refetchOnWindowFocus: false,
        });

    const { isPending: updateOrderPending, mutateAsync: updateOrder } =
        useMutation({
            mutationKey: [`update-video-order`],
            mutationFn: ({
                videoId,
                newOrder,
            }: {
                videoId: string;
                newOrder: number;
            }) => updatePlaylistVideoOrder(playlistId, videoId, newOrder),
        });

    useEffect(() => {
        if (!data) return;
        setVideos(data.pages.flatMap(({ content }: any) => content ?? []));
    }, [data]);

    useEffect(() => {
        if (inView && hasNextPage) {
            fetchNextPage();
        }
    }, [inView]);

    // Thiết lập cảm biến cho việc kéo bằng chuột và cảm ứng (touch)
    const sensors = useSensors(useSensor(MouseSensor), useSensor(TouchSensor));

    const handleDragEnd = async (event: any) => {
        if (!data) return;
        const { active, over } = event;

        // Nếu có mục hợp lệ để kéo thả
        if (over && active.id !== over.id) {
            const oldIndex = videos.findIndex(
                (video) => video.id === active.id
            );
            const newIndex = videos.findIndex((video) => video.id === over.id);
            updateOrder({
                videoId: active.id,
                newOrder: newIndex + 1,
            });
            setVideos((videos) => arrayMove(videos, oldIndex, newIndex));
            // try {
            //     await ;
            //     toast.success("oke");
            // } catch (error: any) {
            //     toast.error(error);
            // }
        }
    };

    if (!data) return <>no data</>;

    return (
        <div className="p-5">
            <DndContext
                sensors={sensors}
                collisionDetection={closestCenter}
                onDragEnd={handleDragEnd}
            >
                <SortableContext
                    items={videos}
                    strategy={verticalListSortingStrategy}
                >
                    {videos.map((video: PlaylistVideo, index, content) => {
                        const lastIndex = content.length - 1;
                        if (index === Math.ceil(lastIndex * 0.8))
                            return (
                                <PlaylistVideoDragableItem
                                    ref={ref}
                                    id={video.id}
                                    video={video}
                                ></PlaylistVideoDragableItem>
                            );
                        return (
                            <PlaylistVideoDragableItem
                                id={video.id}
                                video={video}
                            ></PlaylistVideoDragableItem>
                        );
                    })}
                </SortableContext>
            </DndContext>
            {isFetchingNextPage && <p>đang tải....</p>}

            {/* {!hasNextPage && status !== "pending" && <p>Hết rồi</p>} */}
        </div>
    );
};

export default PlaylistVideoDragableList;
