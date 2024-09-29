import Header from "@/components/header/Header";
import { AuthContext } from "@/context/AuthContext";
import { createRootRouteWithContext, Outlet } from "@tanstack/react-router";

interface AppRouterContext {
    auth: AuthContext;
}

export const Route = createRootRouteWithContext<AppRouterContext>()({
    component: Layout,
});

function Layout() {
    return (
        <div className="dark:bg-slate-800 dark:text-white h-[100svh] custom-scroll">
            <Header></Header>
            <Outlet></Outlet>
        </div>
    );
}
