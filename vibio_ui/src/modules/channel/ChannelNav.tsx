import { Link, ReactNode } from "@tanstack/react-router";

type Props = {
    channelId: string;
};

const ChannelNav = ({ channelId }: Props) => {
    return (
        <div className="my-4 flex justify-start items-start gap-5">
            <ChannelNavItem channelId={channelId} to="/">
                Trang chủ
            </ChannelNavItem>
            <ChannelNavItem channelId={channelId} to="/videos">
                Video
            </ChannelNavItem>
            <ChannelNavItem channelId={channelId} to="/playlists">
                Danh sách phát
            </ChannelNavItem>
            <ChannelNavItem channelId={channelId} to="/community">
                Cộng đồng
            </ChannelNavItem>
        </div>
    );
};

type ChannelNavItemProps = {
    to: string;
    children: ReactNode;
    channelId: string;
};

const ChannelNavItem = ({ to, children, channelId }: ChannelNavItemProps) => {
    return (
        <Link
            activeProps={{
                className: "text-primary underline underline-offset-8",
            }}
            className="text-muted-foreground font-semibold transition-all "
            params={{ channelId }}
            to={`/channel/$channelId/${to}`}
            activeOptions={{
                exact: true,
            }}
        >
            {children}
        </Link>
    );
};

export default ChannelNav;
