import Header from "@/components/header/Header";
import { AuthContext } from "@/context/AuthContext";
import { createRootRouteWithContext, Outlet } from "@tanstack/react-router";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
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
            <ToastContainer
                position="top-right"
                autoClose={4000}
                limit={4}
                hideProgressBar={false}
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
                theme="light"
            />
        </div>
    );
}
