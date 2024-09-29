import {createFileRoute} from "@tanstack/react-router";
import {Button} from "../../components/ui/button";
import {useAuth} from "../../context/AuthContext";

export const Route = createFileRoute("/(app)/")({
    component: Home,
});

function Home() {
    const {logout} = useAuth();
    return (
        <div>
            Hello home
            <Button onClick={() => logout()}>logout</Button>
        </div>
    );
}
