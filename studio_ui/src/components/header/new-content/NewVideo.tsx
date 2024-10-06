import { uploadVideo } from "@/api/video";
import InputVideo from "@/components/input/InputVideo";
import { Button } from "@/components/ui/button";
import { useMutation } from "@tanstack/react-query";
import { useNavigate } from "@tanstack/react-router";
import FormData from "form-data";
import { useCallback } from "react";
import { FiUploadCloud } from "react-icons/fi";
import { ImageType } from "react-images-uploading";
import { toast } from "react-toastify";

type Props = {};

const NewVideo = (props: Props) => {
    const uploadMuatation = useMutation({
        mutationKey: ["upload", "video"],
        mutationFn: (data: FormData) => uploadVideo(data),
    });

    const navigation = useNavigate();

    const handleUploadVideo = useCallback(async (video: ImageType) => {
        const formData = new FormData();
        formData.append("video", video.file || null);
        try {
            const data: any = await uploadMuatation.mutateAsync(formData);
            toast.success("Video đã được tải lên thành công");
            navigation({
                to: "/videos/$videoId/edit",
                params: {
                    videoId: data.videoId,
                },
            });
        } catch (error: any) {
            console.log({ error });
            toast.error(error);
        }
    }, []);

    return (
        <InputVideo onSave={handleUploadVideo}>
            <Button
                variant="ghost"
                className="flex w-full  justify-start items-center  gap-3"
            >
                <FiUploadCloud size={16} />
                <span>Tải video</span>
            </Button>
        </InputVideo>
    );
};

export default NewVideo;
