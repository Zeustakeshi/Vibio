import InputVideo from "@/components/input/InputVideo";
import { Button } from "@/components/ui/button";
import { useCallback } from "react";
import { FiUploadCloud } from "react-icons/fi";
import { ImageType } from "react-images-uploading";

type Props = {};

const NewVideo = (props: Props) => {
    const handleUploadVideo = useCallback(async (video: ImageType) => {
        console.log({ video });
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
