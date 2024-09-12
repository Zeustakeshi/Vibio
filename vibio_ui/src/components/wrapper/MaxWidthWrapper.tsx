import React from "react";
import { cn } from "../../lib/utils";
import { ReactNode } from "@tanstack/react-router";

type Props = {
    className?: string;
    children?: ReactNode;
};

const MaxWidthWrapper = ({ className, children }: Props) => {
    return (
        <div className={cn("mx-auto max-w-screen-2xl md:px-5", className)}>
            {children}
        </div>
    );
};

export default MaxWidthWrapper;
