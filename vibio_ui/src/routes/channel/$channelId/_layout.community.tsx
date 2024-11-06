import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/channel/$channelId/_layout/community")({
    component: () => <div>Hello /channel/$channelId/community!</div>,
});
