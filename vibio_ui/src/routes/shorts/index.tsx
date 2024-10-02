import { createFileRoute } from "@tanstack/react-router";
import { FaAngleDown, FaAngleUp } from "react-icons/fa";
import { Button } from "../../components/ui/button";
import ShortVideoPlayer from "../../components/video/short/ShortVideoPlayer";
import ShortVideoAction from "../../modules/short/ShortVideoAction";

import { Swiper, SwiperSlide } from "swiper/react";

// Import Swiper styles
import "swiper/css";
import "swiper/css/pagination";

// import required modules
import { Mousewheel } from "swiper/modules";

export const Route = createFileRoute("/shorts/")({
    component: Shorts,
});

function Shorts() {
    return (
        <div className="w-full h-full flex justify-between items-center p-2">
            <Swiper
                direction={"vertical"}
                slidesPerView={1}
                spaceBetween={30}
                mousewheel={true}
                pagination={{
                    clickable: true,
                }}
                modules={[Mousewheel]}
                className="hidden-scroll  h-full pt-4 flex flex-col gap-3 justify-start items-center flex-1 max-h-[calc(100svh-80px)] overflow-y-scroll"
            >
                {new Array(3).fill(0).map((_, index) => (
                    <SwiperSlide
                        key={index}
                        className="h-full flex justify-center items-center gap-2"
                    >
                        {({ isActive }) => (
                            <>
                                <ShortVideoPlayer
                                    isActive={isActive}
                                    url={
                                        "https://res.cloudinary.com/dymmvrufy/video/upload/v1727843028/vibio/videos/tn235l9gz4hycobxnxbm.mp4"
                                    }
                                ></ShortVideoPlayer>
                                <ShortVideoAction></ShortVideoAction>
                            </>
                        )}
                    </SwiperSlide>
                ))}
            </Swiper>
            <div className="flex items-center justify-center gap-2 flex-col">
                <Button variant="secondary">
                    <FaAngleUp />
                </Button>
                <Button variant="secondary">
                    <FaAngleDown />
                </Button>
            </div>
        </div>
    );
}
