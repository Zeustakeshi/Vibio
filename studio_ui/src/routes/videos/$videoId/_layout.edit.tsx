import { getVideoById, updateVideo } from "@/api/video";
import { VideoVisibility } from "@/common/type/video.type";
import { Checkbox } from "@/components/ui/checkbox";
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from "@/components/ui/form";
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select";
import FormHeader from "@/modules/video/edit/FormHeader";
import FormMetadata from "@/modules/video/edit/FormMetadata";
import VideoThumbnail from "@/modules/video/edit/VideoThumbnail";
import { updateVideoSchema } from "@/schema/video.schema";
import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation, useQuery } from "@tanstack/react-query";
import { createFileRoute } from "@tanstack/react-router";
import { useCallback, useEffect } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { toast } from "react-toastify";
import { z } from "zod";

export const Route = createFileRoute("/videos/$videoId/_layout/edit")({
    component: EditVideo,
});

function EditVideo() {
    const { videoId } = Route.useParams();

    const { data: video, isLoading } = useQuery({
        queryKey: ["video", "detail", videoId],
        queryFn: () => getVideoById(videoId),
    });

    const updateVideoMutation = useMutation({
        mutationKey: ["update", "video", videoId],
        mutationFn: (video: z.infer<typeof updateVideoSchema>) =>
            updateVideo(videoId, video),
    });

    const form = useForm<z.infer<typeof updateVideoSchema>>({
        resolver: zodResolver(updateVideoSchema),
    });

    useEffect(() => {
        if (isLoading || !video) return;

        form.setValue("title", video.title);
        form.setValue("description", video.description);
        form.setValue("tags", video.tags);
        form.setValue("visibility", video.visibility);
        form.setValue("allowedComment", video.allowedComment);
    }, [video]);

    const onSubmit = useCallback(
        async (value: z.infer<typeof updateVideoSchema>) => {
            try {
                await updateVideoMutation.mutateAsync(value);
                toast.success("Cập nhật video thành công");
            } catch (error: any) {
                console.log({ error });
                toast.error(error);
            }
        },
        []
    );
    if (isLoading || !video)
        return (
            <div className="min-h-[300px] w-full flex justify-center items-center">
                <div className="size-[32px] rounded-full border-2 border-primary animate-spin border-t-transparent"></div>
            </div>
        );

    return (
        <FormProvider {...form}>
            <Form {...form}>
                <form onSubmit={form.handleSubmit(onSubmit)}>
                    <FormHeader
                        loading={updateVideoMutation.isPending}
                    ></FormHeader>
                    {/* Content */}
                    <div className="grid grid-cols-3 gap-x-5 h-full">
                        <FormMetadata></FormMetadata>
                        <div className="col-span-1 ">
                            <h4 className="mb-3 font-semibold">Video</h4>
                            <video
                                className="aspect-video rounded-xl max-h-[320px]"
                                controls
                                src={video?.url}
                            ></video>

                            <VideoThumbnail
                                defaultValue={video?.thumbnail}
                                videoId={videoId}
                            ></VideoThumbnail>

                            <h4 className="my-3 font-semibold">Tùy chỉnh</h4>
                            <FormField
                                control={form.control}
                                name="visibility"
                                render={({ field }) => (
                                    <FormItem className="my-4">
                                        <FormLabel>Chế độ hiển thị</FormLabel>
                                        <Select
                                            onValueChange={field.onChange}
                                            defaultValue={field.value}
                                        >
                                            <FormControl>
                                                <SelectTrigger>
                                                    <SelectValue placeholder="Chọn chế độ hiện thị" />
                                                </SelectTrigger>
                                            </FormControl>
                                            <SelectContent>
                                                <SelectItem
                                                    value={
                                                        VideoVisibility.PUBLIC
                                                    }
                                                >
                                                    Công khai
                                                </SelectItem>
                                                <SelectItem
                                                    value={
                                                        VideoVisibility.MEMBER
                                                    }
                                                >
                                                    Hội viên
                                                </SelectItem>
                                                <SelectItem
                                                    value={
                                                        VideoVisibility.PRIVATE
                                                    }
                                                >
                                                    Riêng tư
                                                </SelectItem>
                                            </SelectContent>
                                        </Select>

                                        <FormMessage />
                                    </FormItem>
                                )}
                            />

                            <FormField
                                control={form.control}
                                name="allowedComment"
                                render={({ field }) => (
                                    <FormItem className="flex flex-row items-start space-x-3 space-y-0 rounded-md ">
                                        <FormControl>
                                            <Checkbox
                                                checked={field.value}
                                                onCheckedChange={field.onChange}
                                            />
                                        </FormControl>
                                        <div className="space-y-1 leading-none">
                                            <FormLabel>
                                                Cho phép bình luận trong video
                                                này
                                            </FormLabel>
                                        </div>
                                    </FormItem>
                                )}
                            />
                        </div>
                    </div>
                </form>
            </Form>
        </FormProvider>
    );
}
