import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import TextArea from "@/components/ui/text-area";
import { VideoUpdateProvider } from "@/context/VideoUpdateContext";
import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/videos/$videoId/_layout/edit")({
    component: EditVideo,
});

function EditVideo() {
    return (
        <VideoUpdateProvider videoId="1">
            <div className="w-full flex justify-between items-center gap-2 top-[60px] sticky bg-white">
                <div>
                    <h1 className="text-xl font-semibold py-3">
                        Chi tiết video
                    </h1>
                </div>
                <div className="flex justify-end items-center gap-2">
                    <Button variant="secondary">Hủy thay đổi</Button>
                    <Button>Lưu</Button>
                </div>
            </div>
            <div className="grid grid-cols-3 gap-x-2 h-full">
                <div className="col-span-2">
                    <div>
                        <h3 className="text-lg my-3">Tiêu đề</h3>
                        <Input placeholder="Tiêu đề video"></Input>
                    </div>
                    <div>
                        <h3 className="text-lg my-3">Mô tả</h3>
                        <TextArea
                            placeholder="Mô tả video"
                            className="min-h-[200px]"
                        ></TextArea>
                    </div>
                </div>
                <div className="col-span-1 bg-primary"></div>
            </div>
        </VideoUpdateProvider>
    );
}
