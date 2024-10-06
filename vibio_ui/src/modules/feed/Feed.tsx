import FeedItem from "./FeedItem";

type Props = {};

const Feeds = (props: Props) => {
    return (
        <div className="grid grid-cols-5 gap-2">
            {new Array(10).fill(0).map((_, index) => (
                <FeedItem key={index}></FeedItem>
            ))}
        </div>
    );
};

export default Feeds;
