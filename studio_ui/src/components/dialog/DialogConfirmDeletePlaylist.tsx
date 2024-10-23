import { deletePlaylist } from "@/api/playlist";
import { cn } from "@/lib/utils";
import { useMutation } from "@tanstack/react-query";
import { ReactNode } from "@tanstack/react-router";
import { useState } from "react";
import { toast } from "react-toastify";
import {
    AlertDialog,
    AlertDialogCancel,
    AlertDialogContent,
    AlertDialogDescription,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogTitle,
    AlertDialogTrigger,
} from "../ui/alert-dialog";
import { Button, buttonVariants } from "../ui/button";

type Props = {
    playlistId: string;
    children: ReactNode;
};

const DialogConfirmDeletePlaylist = ({ playlistId, children }: Props) => {
    const [open, setOpen] = useState<boolean>(false);

    const { mutateAsync, isPending } = useMutation({
        mutationKey: ["delete-playlist", playlistId],
        mutationFn: () => deletePlaylist(playlistId),
    });

    const handleDeletePlaylist = async () => {
        try {
            await mutateAsync();
            toast.success("Xóa thành công.");
            setOpen(false);
        } catch (error: any) {
            toast.error(error);
        }
    };

    return (
        <AlertDialog open={open} onOpenChange={(open) => setOpen(open)}>
            <AlertDialogTrigger asChild>{children}</AlertDialogTrigger>
            <AlertDialogContent>
                <AlertDialogHeader>
                    <AlertDialogTitle>
                        Bạn có chắc muốn xóa danh sách này không?
                    </AlertDialogTitle>
                    <AlertDialogDescription>
                        Hành động này không thể khôi phục. Danh sách của bạn sẽ
                        bị xóa vĩnh viễn khỏi server của chúng tôi
                    </AlertDialogDescription>
                </AlertDialogHeader>
                <AlertDialogFooter>
                    <AlertDialogCancel>Hủy</AlertDialogCancel>
                    <Button
                        onClick={handleDeletePlaylist}
                        disabled={isPending}
                        className={cn(
                            buttonVariants({
                                variant: "destructive",
                            })
                        )}
                    >
                        {isPending ? "Đang xử lý" : "Xóa"}
                    </Button>
                </AlertDialogFooter>
            </AlertDialogContent>
        </AlertDialog>
    );
};

export default DialogConfirmDeletePlaylist;
