import { getPlaylistById, updatePlaylist } from "@/api/playlist";
import { Visibility } from "@/common/enum";
import DialogConfirmDeletePlaylist from "@/components/dialog/DialogConfirmDeletePlaylist";
import { Button } from "@/components/ui/button";
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select";
import { updatePlaylistSchema } from "@/schema/playlist.schema";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation, useQuery } from "@tanstack/react-query";
import { createFileRoute } from "@tanstack/react-router";
import { useCallback, useEffect } from "react";
import { useForm } from "react-hook-form";
import { toast } from "react-toastify";
import { z } from "zod";

import PlaylistVideoDragableList from "@/modules/content/playlist/PlaylistVideoDragableList";

export const Route = createFileRoute("/playlist/$playlistId/_layout/edit")({
    component: EditPlaylist,
});

function EditPlaylist() {
    const { playlistId } = Route.useParams();

    const form = useForm<z.infer<typeof updatePlaylistSchema>>({
        resolver: zodResolver(updatePlaylistSchema),
    });

    const { data: playlist, isLoading } = useQuery({
        queryKey: ["playlist", "detail", playlistId],
        queryFn: () => getPlaylistById(playlistId),
    });

    const updatePlaylistMutation = useMutation({
        mutationKey: ["update", "playlist", playlistId],
        mutationFn: (playlist: z.infer<typeof updatePlaylistSchema>) =>
            updatePlaylist(playlistId, playlist),
    });

    useEffect(() => {
        if (isLoading || !playlist) return;
        form.setValue("name", playlist?.name);
        form.setValue("visibility", playlist.visibility);
    }, [playlist]);

    const onSubmit = useCallback(
        async (value: z.infer<typeof updatePlaylistSchema>) => {
            try {
                await updatePlaylistMutation.mutateAsync(value);
                toast.success("Cập nhật danh sách thành công");
            } catch (error: any) {
                console.log({ error });
                toast.error(error);
            }
        },
        []
    );
    if (isLoading || !playlist)
        return (
            <div className="min-h-[300px] w-full flex justify-center items-center">
                <div className="size-[32px] rounded-full border-2 border-primary animate-spin border-t-transparent"></div>
            </div>
        );

    return (
        <div className="flex justify-start items-start gap-4 w-full h-full ">
            <Form {...form}>
                <form
                    onSubmit={form.handleSubmit(onSubmit)}
                    className="rounded-xl min-w-[400px] overflow-hidden  h-full p-5 flex flex-col"
                >
                    <div className="flex-1">
                        <div className="mt-4 mb-8 w-full h-[200px] border rounded-xl overflow-hidden">
                            <img
                                className="w-full h-full object-cover"
                                src={playlist.defaultThumbnail}
                                alt=""
                            />
                        </div>

                        <FormField
                            control={form.control}
                            name="name"
                            render={({ field }) => (
                                <FormItem>
                                    <FormLabel>Tên danh sách</FormLabel>
                                    <FormControl {...field}>
                                        <Input
                                            placeholder="Tên danh sách phát"
                                            {...field}
                                        ></Input>
                                    </FormControl>
                                    <FormMessage></FormMessage>
                                </FormItem>
                            )}
                        />

                        <FormField
                            control={form.control}
                            name="visibility"
                            render={({ field }) => (
                                <FormItem className="my-4">
                                    <FormLabel>Chế độ hiển thị</FormLabel>
                                    <Select
                                        onValueChange={field.onChange}
                                        value={field.value}
                                    >
                                        <FormControl>
                                            <SelectTrigger>
                                                <SelectValue placeholder="Chọn chế độ hiện thị" />
                                            </SelectTrigger>
                                        </FormControl>
                                        <SelectContent>
                                            <SelectItem
                                                value={Visibility.PUBLIC}
                                            >
                                                Công khai
                                            </SelectItem>
                                            <SelectItem
                                                value={Visibility.MEMBER}
                                            >
                                                Hội viên
                                            </SelectItem>
                                            <SelectItem
                                                value={Visibility.PRIVATE}
                                            >
                                                Riêng tư
                                            </SelectItem>
                                        </SelectContent>
                                    </Select>

                                    <FormMessage />
                                </FormItem>
                            )}
                        />
                    </div>
                    <div className="flex justify-end items-center gap-2 my-5">
                        <DialogConfirmDeletePlaylist playlistId={playlist.id}>
                            <Button
                                variant="destructive"
                                type="button"
                                className=""
                            >
                                Xóa danh sách
                            </Button>
                        </DialogConfirmDeletePlaylist>
                        <Button
                            disabled={updatePlaylistMutation.isPending}
                            type="submit"
                        >
                            {updatePlaylistMutation.isPending
                                ? "Đang cập nhật"
                                : "Cập nhật"}
                        </Button>
                    </div>
                </form>
            </Form>

            <div className="flex-1  h-full rounded-xl overflow-hidden">
                <PlaylistVideoDragableList
                    playlistId={playlist.id}
                ></PlaylistVideoDragableList>
            </div>
        </div>
    );
}
