import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "@/components/ui/dialog";
import { cn, gigabytesToBytes } from "@/lib/utils";
import { X } from "lucide-react";
import { ReactNode, useState } from "react";
import ImageUploading, {
    ImageListType,
    ImageType,
} from "react-images-uploading";
import { Button } from "../ui/button";

type Props = {
    children: ReactNode;
    className?: string;
    onSave: (video: ImageType) => Promise<void> | void;
};

const InputVideo = ({ children, className, onSave }: Props) => {
    const [open, setOpen] = useState<boolean>(false);
    const [loading, setLoading] = useState<boolean>(false);
    const [images, setImages] = useState<ImageListType>([]);

    const handleSave = async () => {
        if (images.length <= 0) return;
        setLoading(true);
        await onSave(images[0]);
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
                    <DialogTitle>Tải video lên</DialogTitle>
                    <DialogDescription>
                        Chọn video bạn muốn tải lên
                    </DialogDescription>
                </DialogHeader>

                <div className="">
                    <ImageUploading
                        multiple={false}
                        value={images}
                        onChange={(value: ImageListType) => {
                            setImages(value);
                        }}
                        maxNumber={1}
                        allowNonImageType
                        maxFileSize={gigabytesToBytes(1)}
                        acceptType={["mp4"]}
                        dataURLKey="dataURL"
                    >
                        {({
                            imageList,
                            onImageUpload,
                            onImageRemove,
                            dragProps,
                            errors,
                        }) => (
                            <div>
                                <div
                                    className={cn(
                                        "relative h-[300px] border border-dashed border-primary rounded-xl custom-scroll overflow-hidden"
                                    )}
                                >
                                    {!imageList.length && !loading && (
                                        <div
                                            className="w-full h-full bg-upload bg-no-repeat bg-contain bg-center"
                                            onClick={onImageUpload}
                                            {...dragProps}
                                        ></div>
                                    )}

                                    {imageList.map((image, index) => (
                                        <div
                                            key={index}
                                            className="relative image-item flex flex-col justify-start items-start h-full"
                                        >
                                            <div className="w-full h-full abs-center">
                                                <video
                                                    controls
                                                    src={image.dataURL}
                                                    className="object-contain w-full h-full"
                                                />
                                            </div>
                                            {!loading && (
                                                <Button
                                                    onClick={() =>
                                                        onImageRemove(index)
                                                    }
                                                    variant="ghost"
                                                    size="icon"
                                                    className="z-20 absolute top-0 right-0"
                                                >
                                                    <X size={20} />
                                                </Button>
                                            )}
                                        </div>
                                    ))}
                                </div>

                                {errors && (
                                    <div className="text-xs text-rose-500 ">
                                        {errors.maxNumber && (
                                            <span>
                                                Bạn chỉ chọn được tối đa 1 file
                                            </span>
                                        )}
                                        {errors.acceptType && (
                                            <span>
                                                Bạn chỉ có thể chọn file .mp4
                                            </span>
                                        )}
                                        {errors.maxFileSize && (
                                            <span>
                                                Bạn chỉ có thể tải lên video
                                                dưới 1G
                                            </span>
                                        )}
                                        {errors.resolution && (
                                            <span>
                                                Selected file is not match your
                                                desired resolution
                                            </span>
                                        )}
                                    </div>
                                )}
                            </div>
                        )}
                    </ImageUploading>
                </div>
                <DialogFooter>
                    <Button
                        disabled={loading}
                        type="button"
                        onClick={handleSave}
                    >
                        {loading ? "Đang xử lý" : "Tải lên"}
                    </Button>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    );
};

export default InputVideo;
