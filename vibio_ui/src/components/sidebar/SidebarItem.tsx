import { Link, ReactNode } from "@tanstack/react-router";
import { cn } from "../../lib/utils";
import { buttonVariants } from "../ui/button";

type Props = {
    children: ReactNode;
    icon: ReactNode;
    to: string;
};

const SidebarItem = ({ children, icon, to }: Props) => {
    return (
        <Link
            to={to}
            className={cn(
                buttonVariants({ variant: "ghost" }),
                "w-full justify-start items-center gap-4 font-normal"
            )}
            activeProps={{
                className:
                    "bg-primary text-white hover:bg-primary hover:text-white",
            }}
        >
            {icon}
            {children}
        </Link>
    );
};

export default SidebarItem;
