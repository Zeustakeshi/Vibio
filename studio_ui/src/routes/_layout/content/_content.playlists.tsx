import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/_layout/content/_content/playlists")({
    component: () => <div>Hello /content/_content/playlists!</div>,
});
