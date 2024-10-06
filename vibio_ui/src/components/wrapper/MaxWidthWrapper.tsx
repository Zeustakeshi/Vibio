import { ReactNode } from "@tanstack/react-router";
import { cn } from "../../lib/utils";

type Props = {
    className?: string;
    children?: ReactNode;
};

const MaxWidthWrapper = ({ className, children }: Props) => {
    return (
        <div className={cn("mx-auto max-w-screen-2xl md:px-2", className)}>
            {children}
        </div>
    );
};

export default MaxWidthWrapper;
