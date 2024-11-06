import {
    Carousel,
    CarouselContent,
    CarouselItem,
    CarouselNext,
    CarouselPrevious,
} from "../../../components/ui/carousel";
import ChannelSession from "../ChannelSession";
import ChannelVideoItem from "../ChannelVideoItem";

type Props = {};

const PopularVideo = (props: Props) => {
    return (
        <ChannelSession title="Video phổ biến" className="flex justify-center ">
            <Carousel
                opts={{
                    align: "start",
                }}
                className="w-full max-w-[85%] my-4 "
            >
                <CarouselContent>
                    {Array.from({ length: 10 }).map((_, index) => (
                        <CarouselItem
                            key={index}
                            className="md:basis-1/2 lg:basis-1/4"
                        >
                            <ChannelVideoItem></ChannelVideoItem>
                        </CarouselItem>
                    ))}
                </CarouselContent>
                <CarouselPrevious />
                <CarouselNext />
            </Carousel>
        </ChannelSession>
    );
};

export default PopularVideo;
