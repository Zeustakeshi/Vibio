import { createFileRoute } from "@tanstack/react-router";
import Feed from "../../modules/feed/Feed";

export const Route = createFileRoute("/(app)/")({
    component: Home,
});

function Home() {
    return (
        <div>
            <Feed></Feed>
        </div>
    );
}
