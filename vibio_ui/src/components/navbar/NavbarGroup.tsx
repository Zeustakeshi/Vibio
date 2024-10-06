import { ReactNode } from "@tanstack/react-router";
import { cn } from "../../lib/utils";

type Props = {
    children: ReactNode;
    headerLabel?: string;
};

const NavbarGroup = ({ children, headerLabel }: Props) => {
    return (
        <div className={cn("space-y-1 px-1")}>
            {headerLabel && (
                <h3 className="font-semibold mx-3 my-2">{headerLabel}</h3>
            )}

            {children}
        </div>
    );
};

export default NavbarGroup;
