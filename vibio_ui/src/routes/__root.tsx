import { Outlet, createRootRouteWithContext } from "@tanstack/react-router";
import * as React from "react";
import Header from "../components/header/Header";
import { AuthContext } from "../context/AuthContext";

interface AppRouterContext {
    auth: AuthContext;
}

export const Route = createRootRouteWithContext<AppRouterContext>()({
    component: () => (
        <React.Fragment>
            <Header></Header>
            <Outlet />
        </React.Fragment>
    ),
});
