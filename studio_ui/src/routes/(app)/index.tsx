import { useAuth } from "@/context/AuthContext";
import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/(app)/")({
    component: Home,
});

function Home() {
    const auth = useAuth();
    return <>{JSON.stringify(auth.channel)}</>;
}
