import { uploadThumbnail } from "@/api/video";
import InputImage from "@/components/input/InputImage";
import { cn } from "@/lib/utils";
import { useMutation } from "@tanstack/react-query";
import FormData from "form-data";
import { useCallback, useState } from "react";
import { ImageListType } from "react-images-uploading";
import { toast } from "react-toastify";

type Props = {
    defaultValue?: string;
    videoId: string;
};
const VideoThumbnail = ({ defaultValue, videoId }: Props) => {
    const [thumbnail, setThumbnail] = useState<string | undefined>(
        defaultValue
    );

    const uploadThumbnailMutation = useMutation({
        mutationKey: ["video", "thumbnail", "upload", videoId],
        mutationFn: (data: FormData) => uploadThumbnail(videoId, data),
    });

    const handleSaveVideo = useCallback(async (image: ImageListType) => {
        try {
            const formData = new FormData();
            formData.append("thumbnail", image[0]?.file);
            await uploadThumbnailMutation.mutateAsync(formData);
            toast.success("Tải ảnh bìa lên thành công");
            setThumbnail(image[0].dataURL);
        } catch (error: any) {
            console.log({ error });
            toast.error(error);
        }
    }, []);

    return (
        <div>
            <h4 className="my-3  font-semibold">Ảnh bìa video</h4>
            <InputImage onSave={handleSaveVideo}>
                <div
                    className={cn(
                        "aspect-video rounded-xl max-h-[320px] overflow-hidden ",
                        {
                            "border-2 border-dashed border-primary relative":
                                !thumbnail,
                        }
                    )}
                >
                    <img
                        className="w-full h-full object-cover"
                        src={thumbnail}
                        alt=""
                    />
                </div>
            </InputImage>
        </div>
    );
};

export default VideoThumbnail;
