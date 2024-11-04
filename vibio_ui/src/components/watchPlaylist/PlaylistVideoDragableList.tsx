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
import { useInfiniteQuery } from "@tanstack/react-query";
import { useEffect } from "react";
import {
    getAllVideoByPlaylistId,
    getAllVideoPublicByPlaylistId,
} from "../../api/playlist";
import { useAuth } from "../../context/AuthContext";
import { usePlaylistControl } from "../../context/PlaylistControlContext";
import PlaylistVideoDragableItem from "./PlaylistVideoDragableItem";

type Props = {
    playlistId: string;
};

const PlaylistVideoDragableList = ({ playlistId }: Props) => {
    const { isAuthenticated } = useAuth();
    const [ref, inView] = useInView();
    const { playingVideo, videos, setVideos } = usePlaylistControl();

    const {
        data,
        fetchNextPage,
        isFetchingNextPage,
        hasNextPage,
        status,
        isPending,
    } = useInfiniteQuery({
        queryKey: [`playlist-${playlistId}-videos`],
        queryFn: async (pages) => {
            if (isAuthenticated)
                return await getAllVideoByPlaylistId(
                    playlistId,
                    pages.pageParam
                );
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
        if (!data || !playingVideo) return;
        // const playingVideoId = playingVideo.id;
        const videos = data.pages.flatMap(({ content }: any) => content ?? []);

        // const filterVideos = videos.filter(
        //     (video) => video.id !== playingVideoId
        // );

        // const currentVideo = videos.find(
        //     (video) => video.id === playingVideoId
        // );

        setVideos(videos);
    }, [data]);

    useEffect(() => {
        if (inView && hasNextPage) {
            fetchNextPage();
        }
    }, [inView]);

    // Thiết lập cảm biến cho việc kéo bằng chuột và cảm ứng (touch)
    const sensors = useSensors(useSensor(MouseSensor), useSensor(TouchSensor));

    const handleDragEnd = async (event: any) => {
        // if (!data) return;
        const { active, over } = event;

        // Nếu có mục hợp lệ để kéo thả
        if (over && active.id !== over.id) {
            const oldIndex = videos.findIndex(
                (video) => video.id === active.id
            );
            const newIndex = videos.findIndex((video) => video.id === over.id);
            // updateOrder({
            //     videoId: active.id,
            //     newOrder: newIndex + 1,
            // });
            setVideos((videos) => arrayMove(videos, oldIndex, newIndex));
        }
    };

    if (!data) return <>Chưa có video nào</>;

    return (
        <div className="w-full max-w-full  py-2 pb-8">
            <DndContext
                sensors={sensors}
                collisionDetection={closestCenter}
                onDragEnd={handleDragEnd}
            >
                <SortableContext
                    items={videos}
                    strategy={verticalListSortingStrategy}
                >
                    {videos.map((video: any, index, content) => {
                        const lastIndex = content.length - 1;
                        if (index === Math.ceil(lastIndex * 0.8))
                            return (
                                <PlaylistVideoDragableItem
                                    key={index}
                                    ref={ref}
                                    id={video.id}
                                    video={video}
                                ></PlaylistVideoDragableItem>
                            );
                        return (
                            <PlaylistVideoDragableItem
                                video={video}
                                key={index}
                                id={video.id}
                            ></PlaylistVideoDragableItem>
                        );
                    })}
                </SortableContext>
            </DndContext>
            {isFetchingNextPage && <p>đang tải....</p>}

            {!hasNextPage && status !== "success" && <p>Hết rồi</p>}
        </div>
    );
};

export default PlaylistVideoDragableList;
