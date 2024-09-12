import * as React from "react";
import { Outlet, createRootRoute } from "@tanstack/react-router";
import Header from "../components/header/Header";

export const Route = createRootRoute({
    component: () => (
        <React.Fragment>
            <Header></Header>
            <Outlet />
        </React.Fragment>
    ),
});
