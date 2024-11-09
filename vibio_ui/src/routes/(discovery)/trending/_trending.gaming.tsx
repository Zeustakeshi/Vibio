import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/(discovery)/trending/_trending/gaming")({
    component: () => <div>Hello /(discovery)/trending/_trending/gaming!</div>,
});
