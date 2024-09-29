import { cn } from "@/lib/utils";
import { Link } from "@tanstack/react-router";
import { ReactNode } from "react";
import { IconType } from "react-icons/lib";
import { buttonVariants } from "../ui/button";

type Props = {
    Icon: IconType;
    children: ReactNode;
    to: string;
};

const SidebarItem = ({ Icon, children, to }: Props) => {
    return (
        <Link
            to={to}
            className={cn(
                buttonVariants({ variant: "ghost" }),
                "flex justify-start items-center gap-3 w-full hover:text-primary hover:bg-primary/5"
            )}
            activeOptions={{ includeHash: true }}
            activeProps={{ className: "text-primary bg-primary/5" }}
        >
            <Icon size={20}></Icon>
            <div className="flex-1 inline-block text-start">{children}</div>
        </Link>
    );
};

export default SidebarItem;
