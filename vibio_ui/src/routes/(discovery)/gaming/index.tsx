import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/(discovery)/gaming/")({
    component: () => <div>Hello /(discovery)/gaming/!</div>,
});
