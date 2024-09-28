import { AuthContext } from "@/context/AuthContext";
import { createRootRouteWithContext, Outlet } from "@tanstack/react-router";

interface AppRouterContext {
    auth: AuthContext;
}

export const Route = createRootRouteWithContext<AppRouterContext>()({
    component: () => (
        <>
            <h1>_________________ header _________</h1>
            <Outlet />
        </>
    ),
});
