import { Link, ReactNode } from "@tanstack/react-router";
import { useSelector } from "react-redux";
import { cn } from "../../lib/utils";
import { selectApp } from "../../store/features/app/appSlice";

type Props = {
    children: ReactNode;
    icon: ReactNode;
    to: string;
};

const NavbarItem = ({ children, icon, to }: Props) => {
    const { navState } = useSelector(selectApp);
    return (
        <Link
            to={to}
            className={cn(
                " justify-start items-center gap-4 font-normal px-4 py-2 rounded-md text-center flex w-[200px] hover:bg-slate-200 transition-all hover:text-slate-900",
                {
                    "!p-2 flex flex-col gap-2 !w-[40px]":
                        navState === "half-open",
                }
            )}
            activeProps={{
                className:
                    "bg-primary text-white !hover:bg-primary hover:text-white",
            }}
        >
            <div>{icon}</div>
            {navState === "open" && (
                <div className="text-sm line-clamp-1 text-left">{children}</div>
            )}
        </Link>
    );
};

export default NavbarItem;
