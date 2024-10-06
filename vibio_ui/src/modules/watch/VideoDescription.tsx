import { useState } from "react";
import { cn } from "../../lib/utils";
import ChannelAction from "./ChannelAction";

type Props = {};

const VideoDescription = (props: Props) => {
    const [collapse, setCollapse] = useState<boolean>(true);

    return (
        <div
            className={cn("my-5 bg-slate-100 p-4 rounded-lg transition-all", {
                "h-[130px] overflow-hidden": collapse,
            })}
        >
            <h5 className="font-semibold text-sm space-x-2">
                <span>1,6 Tr lượt xem</span>
                <span>2 năm trước</span>
            </h5>
            <p className={cn("my-2", { "line-clamp-2": collapse })}>
                Lorem ipsum dolor sit, amet consectetur adipisicing elit.
                Reiciendis, officiis maxime? Repellat facere explicabo, nesciunt
                nisi ipsam modi aut magnam porro enim quibusdam vel ipsum
                assumenda eligendi aliquam quae adipisci placeat nihil facilis
                reprehenderit molestias? Est repudiandae ab blanditiis fugit
                rerum asperiores praesentium nemo eaque dolores aut. Iusto,
                tempore sequi! Harum illo, odio, aliquid asperiores aliquam
                beatae et repellendus incidunt optio impedit hic deserunt
                cupiditate velit culpa. Amet laborum vel ut distinctio,
                molestias impedit corporis assumenda fugiat sit, delectus
                aliquam eum voluptatibus recusandae? Dolore suscipit dolorem
                deserunt debitis quas culpa, ipsum, sed pariatur fuga, aliquid
                minima sit vel officiis cupiditate?
            </p>
            {!collapse && (
                <div className="my-4">
                    <ChannelAction></ChannelAction>
                </div>
            )}
            <p
                onClick={() => setCollapse((prev) => !prev)}
                className="text-xs cursor-pointer hover:font-semibold transition-all"
            >
                {collapse ? "Xem thêm" : "Ẩn bớt"}
            </p>
        </div>
    );
};

export default VideoDescription;
