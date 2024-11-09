import { Link, ReactNode } from "@tanstack/react-router";

type Props = {};

const TrendingNav = (props: Props) => {
    return (
        <div className="flex justify-start items-center gap-3 mb-3">
            <TrendingNavItem to="/trending">Mới nhất</TrendingNavItem>
            <TrendingNavItem to="/trending/movie">Phim ảnh</TrendingNavItem>
            <TrendingNavItem to="/trending/music">Âm nhạc</TrendingNavItem>
            <TrendingNavItem to="/trending/gaming">Trò chơi</TrendingNavItem>
        </div>
    );
};

type TrendingNavItemProps = {
    children: ReactNode;
    to: string;
};

const TrendingNavItem = ({ to, children }: TrendingNavItemProps) => {
    return (
        <Link
            activeProps={{
                className:
                    "underline underline-offset-[6px] text-primary font-semibold ",
            }}
            activeOptions={{
                exact: true,
            }}
            className="text-muted-foreground transition-all font-semibold"
            to={to}
        >
            {children}
        </Link>
    );
};

export default TrendingNav;
