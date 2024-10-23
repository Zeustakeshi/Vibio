import { zodResolver } from "@hookform/resolvers/zod";
import { useInfiniteQuery, useMutation } from "@tanstack/react-query";
import { ReactNode } from "@tanstack/react-router";
import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { useInView } from "react-intersection-observer";
import { z } from "zod";
import {
    addVideoToPlaylist,
    getAllPlaylistByChannelId,
} from "../../api/playlist";
import { Playlist } from "../../common/type/playlist";
import { useAuth } from "../../context/AuthContext";
import { useToast } from "../../hooks/use-toast";
import { addVideoToPlaylistSchema } from "../../schema/playlist.schema";
import { Button } from "../ui/button";
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "../ui/dialog";
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormMessage,
} from "../ui/form";
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "../ui/select";

type Props = {
    videoId: string;
    children: ReactNode;
};

const AddVideoToPlaylistDialog = ({ videoId, children }: Props) => {
    const form = useForm<z.infer<typeof addVideoToPlaylistSchema>>({
        resolver: zodResolver(addVideoToPlaylistSchema),
        defaultValues: {
            videoId: videoId,
        },
    });

    const [ref, inView] = useInView();
    const { toast } = useToast();
    const { isAuthenticated } = useAuth();

    const { data, fetchNextPage, isFetchingNextPage, hasNextPage, status } =
        useInfiniteQuery({
            queryKey: ["user-playlists"],
            queryFn: async (pages) =>
                await getAllPlaylistByChannelId(pages.pageParam ?? 0),
            getNextPageParam: (lastPage: any) => {
                if (lastPage?.last) return undefined;
                return lastPage?.pageNumber + 1;
            },
            initialPageParam: 0,
            refetchOnWindowFocus: false,
            enabled: isAuthenticated,
            gcTime: 15 * 60 * 1000,
        });

    const addVideoToPlaylistMutation = useMutation({
        mutationKey: [`add-video-playlist-${videoId}`],
        mutationFn: (playlistId: string) =>
            addVideoToPlaylist(playlistId, videoId),
    });

    useEffect(() => {
        if (inView && hasNextPage) {
            fetchNextPage();
        }
    }, [inView]);

    const handleAddVideoToPlaylist = async (
        value: z.infer<typeof addVideoToPlaylistSchema>
    ) => {
        if (!isAuthenticated) return;
        try {
            await addVideoToPlaylistMutation.mutateAsync(value.playlistId);
            toast({
                title: "Thành công",
                description: "Thêm video được thành công",
            });
        } catch (error: any) {
            toast({
                title: "Thêm video thất bại",
                description: error,
            });
        }
    };

    return (
        <Dialog>
            <DialogTrigger asChild>{children}</DialogTrigger>
            <DialogContent className="min-h-[200px] items-start flex flex-col justify-start">
                <DialogHeader>
                    <DialogTitle>Lưu video vào:</DialogTitle>
                    <DialogDescription>
                        Lorem ipsum dolor sit amet consectetur, adipisicing
                        elit. Nam, voluptatum.
                    </DialogDescription>
                </DialogHeader>
                <Form {...form}>
                    <form
                        className="w-full flex flex-col gap-y-3"
                        onSubmit={form.handleSubmit(handleAddVideoToPlaylist)}
                    >
                        <FormField
                            control={form.control}
                            name="playlistId"
                            render={({ field }) => (
                                <FormItem>
                                    <Select onValueChange={field.onChange}>
                                        <FormControl {...field}>
                                            <SelectTrigger className="max-w-full w-full overflow-hidden">
                                                <SelectValue placeholder="Chọn danh sách phát" />
                                            </SelectTrigger>
                                        </FormControl>
                                        <SelectContent className="max-h-[300px] overflow-y-scroll custom-scroll">
                                            {data &&
                                                data?.pages
                                                    .flatMap(
                                                        ({ content }: any) =>
                                                            content ?? []
                                                    )
                                                    .map(
                                                        (
                                                            playlist: Playlist,
                                                            index,
                                                            content
                                                        ) => {
                                                            const lastIndex =
                                                                content.length -
                                                                1;
                                                            if (
                                                                index ===
                                                                Math.ceil(
                                                                    lastIndex *
                                                                        0.8
                                                                )
                                                            )
                                                                return (
                                                                    <SelectItem
                                                                        value={
                                                                            playlist.id
                                                                        }
                                                                        ref={
                                                                            ref
                                                                        }
                                                                        className="flex items-center space-x-2 px-3 py-2 max-w-full line-clamp-1"
                                                                    >
                                                                        {
                                                                            playlist.name
                                                                        }
                                                                    </SelectItem>
                                                                );
                                                            return (
                                                                <SelectItem
                                                                    value={
                                                                        playlist.id
                                                                    }
                                                                    className="flex items-center space-x-2 px-3 py-2 max-w-full line-clamp-1"
                                                                >
                                                                    {
                                                                        playlist.name
                                                                    }
                                                                </SelectItem>
                                                            );
                                                        }
                                                    )}

                                            {isFetchingNextPage && (
                                                <p>đang tải....</p>
                                            )}
                                        </SelectContent>
                                    </Select>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />

                        <Button
                            disabled={addVideoToPlaylistMutation.isPending}
                            className="w-full"
                        >
                            {addVideoToPlaylistMutation.isPending
                                ? "Đang xử lý"
                                : "Thêm vào danh sách phát"}
                        </Button>
                    </form>
                </Form>
            </DialogContent>
        </Dialog>
    );
};

export default AddVideoToPlaylistDialog;
