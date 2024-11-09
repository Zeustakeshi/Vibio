import { createFileRoute } from "@tanstack/react-router";
import Feeds from "../../../modules/feed/Feed";

export const Route = createFileRoute("/(discovery)/trending/_trending/")({
    component: TrendingFeed,
});

function TrendingFeed() {
    return (
        <div>
            <Feeds></Feeds>
        </div>
    );
}
