import { ReactNode } from "@tanstack/react-router";
import { cn } from "../../lib/utils";

type Props = {
    title: string;
    children: ReactNode;
    className?: string;
};

const ChannelSession = ({ title, children, className }: Props) => {
    return (
        <div className="my-10">
            <div>
                <h4 className=" !pl-[50px] text-2xl font-semibold my-4">
                    {title}
                </h4>
            </div>
            <div className={cn("", className)}>{children}</div>
        </div>
    );
};

export default ChannelSession;
