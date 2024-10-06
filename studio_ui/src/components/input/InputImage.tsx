import { FC, ReactNode, useState } from "react";

import { Button, buttonVariants } from "@/components/ui/button";
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "@/components/ui/dialog";
import { cn } from "@/lib/utils";
import { Plus } from "lucide-react";
import ImageUploading, { ImageListType } from "react-images-uploading";

type Props = {
    children: ReactNode;
    multiple?: boolean;
    className?: string;
    onSave: (image: ImageListType) => Promise<void> | void;
};

const InputImage: FC<Props> = ({
    children,
    multiple = false,
    className,
    onSave,
}) => {
    const [images, setImages] = useState<ImageListType>([]);
    const [open, setOpen] = useState<boolean>(false);
    const [loading, setLoading] = useState<boolean>(false);
    const maxNumber = 69;

    const onChange = (
        imageList: ImageListType,
        addUpdateIndex?: number[] | undefined
    ) => {
        setImages(imageList);
    };

    const handleSave = async () => {
        if (images.length <= 0) return;
        setLoading(true);
        await onSave(images);
        setLoading(false);
        setOpen(false);
    };

    return (
        <Dialog open={open} onOpenChange={(open) => setOpen(open)}>
            <DialogTrigger className={className} asChild>
                {children}
            </DialogTrigger>
            <DialogContent>
                <DialogHeader>
                    <DialogTitle>Tải ảnh lên</DialogTitle>
                    <DialogDescription>
                        Chọn ảnh bạn muốn tải lên
                    </DialogDescription>
                </DialogHeader>
                <div className="">
                    <ImageUploading
                        multiple={multiple}
                        value={images}
                        onChange={onChange}
                        maxNumber={maxNumber}
                        dataURLKey="dataURL"
                    >
                        {({
                            imageList,
                            onImageUpload,
                            onImageRemoveAll,
                            onImageUpdate,
                            onImageRemove,
                            isDragging,
                            dragProps,
                        }) => (
                            <div>
                                <Button
                                    disabled={loading}
                                    variant="link"
                                    onClick={onImageRemoveAll}
                                >
                                    Xóa tất cả
                                </Button>
                                <div className=" h-[300px] border border-dashed border-primary rounded-xl custom-scroll">
                                    <div className="grid grid-cols-4 gap-3 p-5">
                                        {imageList.map((image, index) => (
                                            <div
                                                key={index}
                                                className="image-item flex flex-col justify-start items-start"
                                            >
                                                <div className=" md:w-20 md:h-20 w-16 h-16 flex justify-center items-center">
                                                    <img
                                                        src={image.dataURL}
                                                        alt=""
                                                        className="object-fill bg-center"
                                                    />
                                                </div>

                                                <div className="flex justify-center gap-2">
                                                    <Button
                                                        disabled={loading}
                                                        className="!p-0  text-[10px] font-semibold"
                                                        variant="link"
                                                        onClick={() =>
                                                            onImageUpdate(index)
                                                        }
                                                    >
                                                        Cập nhật
                                                    </Button>
                                                    <Button
                                                        disabled={loading}
                                                        className="!p-0  text-[10px] font-semibold"
                                                        variant="link"
                                                        onClick={() =>
                                                            onImageRemove(index)
                                                        }
                                                    >
                                                        Xóa
                                                    </Button>
                                                </div>
                                            </div>
                                        ))}
                                        <Button
                                            disabled={loading}
                                            variant="link"
                                            className={cn(
                                                "bg-primary/5 flex justify-center items-center w-10 h-10  md:w-20 md:h-20",
                                                {
                                                    [`${isDragging}`]:
                                                        "text-destructive",
                                                }
                                            )}
                                            onClick={onImageUpload}
                                            {...dragProps}
                                        >
                                            <Plus></Plus>
                                        </Button>
                                    </div>
                                </div>
                            </div>
                        )}
                    </ImageUploading>
                </div>
                <DialogFooter>
                    <Button
                        disabled={loading}
                        className={cn(buttonVariants())}
                        type="button"
                        onClick={handleSave}
                    >
                        {loading ? "Đang xử lý" : "Lưu"}
                    </Button>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    );
};

export default InputImage;
