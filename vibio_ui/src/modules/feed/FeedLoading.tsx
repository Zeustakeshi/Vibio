import { Skeleton } from "../../components/ui/skeleton";

type Props = {};

const FeedLoading = (props: Props) => {
    return (
        <div className="w-full h-full grid gap-4 grid-cols-[repeat(auto-fill,_minmax(250px,_1fr))]">
            {new Array(8).fill(0).map((_, index) => (
                <div className="w-auto" key={index}>
                    <Skeleton
                        key={index}
                        className="aspect-video w-full"
                    ></Skeleton>
                    <div className="flex justify-start items-center gap-3 my-2">
                        <Skeleton className="size-[32px]"></Skeleton>
                        <div>
                            <Skeleton className="my-3 w-[200px] h-3 rounded-full"></Skeleton>
                            <Skeleton className="my-3 w-[140px] h-3 rounded-full"></Skeleton>
                        </div>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default FeedLoading;
