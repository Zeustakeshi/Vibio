import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/channel/$channelId/_layout/shorts")({
    component: () => <div>Hello /channel/$channelId/shorts!</div>,
});
