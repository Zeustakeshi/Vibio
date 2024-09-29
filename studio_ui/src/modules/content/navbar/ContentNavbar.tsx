import ContentNabarItem from "./ContentNabarItem";

type Props = {};

const ContentNavbar = (props: Props) => {
    return (
        <div className="flex justify-start items-center gap-2 px-5 py-1 border-b border-slate-200">
            <ContentNabarItem to="/content/videos">Videos</ContentNabarItem>
            <ContentNabarItem to="/content/shorts">Shorts</ContentNabarItem>
            <ContentNabarItem to="/content/playlists">
                Playlists
            </ContentNabarItem>
            <ContentNabarItem to="/content/posts">Posts</ContentNabarItem>
        </div>
    );
};

export default ContentNavbar;
