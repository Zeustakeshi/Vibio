import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { ReactNode } from "@tanstack/react-router";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { createNewPlaylist } from "../../api/playlist";
import { Visibility } from "../../common/enum";
import { Playlist } from "../../common/type/playlist";
import { Button } from "../../components/ui/button";
import {
    Dialog,
    DialogContent,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "../../components/ui/dialog";
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from "../../components/ui/form";
import { Input } from "../../components/ui/input";
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "../../components/ui/select";
import { useToast } from "../../hooks/use-toast";
import { newPlaylistSchema } from "../../schema/playlist.schema";

type Props = {
    onSaveSuccess?: (playlist: Playlist) => void;
    children: ReactNode;
};

const NewPlaylistDialog = ({ children, onSaveSuccess }: Props) => {
    const form = useForm<z.infer<typeof newPlaylistSchema>>({
        resolver: zodResolver(newPlaylistSchema),
        defaultValues: {
            visibility: Visibility.PRIVATE,
        },
    });

    const newPlaylistMutation = useMutation({
        mutationKey: ["new-playlist"],
        mutationFn: (data: z.infer<typeof newPlaylistSchema>) =>
            createNewPlaylist(data),
    });

    const { toast } = useToast();

    const onSubmit = async (value: z.infer<typeof newPlaylistSchema>) => {
        try {
            const data = await newPlaylistMutation.mutateAsync(value);
            onSaveSuccess?.(data);
            form.reset({
                name: "",
            });
        } catch (error: any) {
            toast({
                title: "Tạo danh sách phát thất bại",
                description: error,
            });
        }
    };

    return (
        <div>
            <Dialog>
                <DialogTrigger asChild>{children}</DialogTrigger>
                <DialogContent>
                    <DialogHeader>
                        <DialogTitle>Danh sách phát mới</DialogTitle>
                        <Form {...form}>
                            <form
                                className="w-full h-full flex flex-col gap-3 !mt-3 justify-start"
                                onSubmit={form.handleSubmit(onSubmit)}
                            >
                                {/* Playlist name */}
                                <FormField
                                    control={form.control}
                                    name="name"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>
                                                Tên danh sách:
                                            </FormLabel>
                                            <FormControl {...field}>
                                                <Input placeholder="Tên danh sách"></Input>
                                            </FormControl>
                                            <FormMessage></FormMessage>
                                        </FormItem>
                                    )}
                                />
                                {/* Visibility */}
                                <FormField
                                    control={form.control}
                                    name="visibility"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>
                                                Chế độ hiện thị:
                                            </FormLabel>
                                            <Select
                                                onValueChange={field.onChange}
                                                defaultValue={Visibility.PRIVATE.toString()}
                                            >
                                                <FormControl>
                                                    <SelectTrigger>
                                                        <SelectValue placeholder="Chọn chế độ hiện thị" />
                                                    </SelectTrigger>
                                                </FormControl>
                                                <SelectContent>
                                                    <SelectItem
                                                        value={Visibility.PUBLIC.toString()}
                                                    >
                                                        Công khai
                                                    </SelectItem>
                                                    <SelectItem
                                                        value={Visibility.MEMBER.toString()}
                                                    >
                                                        Hội viên
                                                    </SelectItem>
                                                    <SelectItem
                                                        value={Visibility.PRIVATE.toString()}
                                                    >
                                                        Riêng tư
                                                    </SelectItem>
                                                </SelectContent>
                                            </Select>
                                            <FormMessage></FormMessage>
                                        </FormItem>
                                    )}
                                />
                                <div className="w-full flex justify-end items-center gap-2 mt-4">
                                    <Button
                                        disabled={newPlaylistMutation.isPending}
                                        type="submit"
                                        className="w-full"
                                    >
                                        {newPlaylistMutation.isPending
                                            ? "Đang tạo mới"
                                            : "Tạo"}
                                    </Button>
                                </div>
                            </form>
                        </Form>
                    </DialogHeader>
                </DialogContent>
            </Dialog>
        </div>
    );
};

export default NewPlaylistDialog;
