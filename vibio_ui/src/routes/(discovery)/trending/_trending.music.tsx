import { createFileRoute } from "@tanstack/react-router";
import Feeds from "../../../modules/feed/Feed";

export const Route = createFileRoute("/(discovery)/trending/_trending/music")({
    component: () => (
        <div>
            <Feeds></Feeds>
        </div>
    ),
});
