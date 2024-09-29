import { Link, ReactNode } from "@tanstack/react-router";

type Props = {
    children: ReactNode;
    to: string;
};

const ContentNabarItem = ({ children, to }: Props) => {
    return (
        <Link
            className=" inline-block px-3 py-2 cursor-pointer font-semibold border-transparent hover:text-primary hover:border-primary border-b-2 transition-all"
            to={to}
            activeOptions={{ exact: true }}
            activeProps={{ className: "text-primary !border-primary" }}
        >
            {children}
        </Link>
    );
};

export default ContentNabarItem;
