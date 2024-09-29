import { ReactNode } from "@tanstack/react-router";

type Props = {
    children: ReactNode;
};

const Sidebar = ({ children }: Props) => {
    return (
        <div className="w-max min-w-[200px] h-[calc(100svh-60px)] sticky top-0 border-r border-slate-200 p-3 flex flex-col justify-start items-center">
            {children}
        </div>
    );
};

export default Sidebar;
