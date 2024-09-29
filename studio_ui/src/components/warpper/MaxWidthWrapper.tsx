import { cn } from "@/lib/utils";
import { ReactNode } from "@tanstack/react-router";

type Props = { children: ReactNode; className?: string };

const MaxWidthWrapper = ({ children, className }: Props) => {
    return (
        <div className={cn("w-full max-w-8xl mx-auto md:px-3 px-1", className)}>
            {children}
        </div>
    );
};

export default MaxWidthWrapper;
