import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/(discovery)/sport/")({
    component: () => <div>Hello /(discovery)/sport/!</div>,
});
