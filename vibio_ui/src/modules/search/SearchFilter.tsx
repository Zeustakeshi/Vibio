import { LuFilter } from "react-icons/lu";
import { Button } from "../../components/ui/button";
import {
    Dialog,
    DialogContent,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "../../components/ui/dialog";

type Props = {};
// type (video, channel, playlist), createdAt (1,2,3, ... hours), order by, Duration

const SearchFilter = (props: Props) => {
    return (
        <div className="flex justify-end items-center gap-5">
            <Dialog>
                <DialogTrigger asChild>
                    <Button
                        variant="secondary"
                        className="flex justify-center items-center gap-2"
                    >
                        <LuFilter />
                        <p>Lọc</p>
                    </Button>
                </DialogTrigger>
                <DialogContent className="min-w-[600px]">
                    <DialogHeader>
                        <DialogTitle>Bộ lọc tìm kiếm</DialogTitle>
                    </DialogHeader>
                    <div className="my-4 flex justify-between items-start gap-3">
                        <div className="flex flex-col justify-start items-center">
                            <h5 className="text-base font-semibold">
                                Ngày tải lên
                            </h5>
                            <div className="text-muted-foreground my-3 space-y-2">
                                <p>Một giờ qua</p>
                                <p>Hôm nay</p>
                                <p>Tuần này</p>
                                <p>Tháng này</p>
                                <p>Năm nay</p>
                            </div>
                        </div>

                        <div className="flex flex-col justify-start items-center">
                            <h5 className="text-base font-semibold">Loại</h5>
                            <div className="text-muted-foreground my-3 space-y-2">
                                <p>Video</p>
                                <p>Kênh</p>
                                <p>Danh sách phát</p>
                            </div>
                        </div>

                        <div className="flex flex-col justify-start items-center">
                            <h5 className="text-base font-semibold">
                                Thời lượng
                            </h5>
                            <div className="text-muted-foreground my-3 space-y-2">
                                <p>5 - 20 phút</p>
                                <p>Trên 20 phút</p>
                            </div>
                        </div>

                        <div className="flex flex-col justify-start items-center">
                            <h5 className="text-base font-semibold">
                                Xắp sếp theo
                            </h5>
                            <div className="text-muted-foreground my-3 space-y-2">
                                <p>Mức độ liên quan</p>
                                <p>Ngày tải lên</p>
                                <p>Lượt xem</p>
                            </div>
                        </div>
                    </div>
                </DialogContent>
            </Dialog>
        </div>
    );
};

export default SearchFilter;
