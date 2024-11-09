import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/(discovery)/news/")({
    component: () => <div>Hello /(discovery)/news/!</div>,
});
