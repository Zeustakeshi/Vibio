import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/(discovery)/music/")({
    component: () => <div>Hello /(discovery)/music/!</div>,
});
