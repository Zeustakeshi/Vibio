import { Outlet, createRootRouteWithContext } from "@tanstack/react-router";
import * as React from "react";
import Header from "../components/header/Header";
import Navbar from "../components/navbar/Navbar";
import MaxWidthWrapper from "../components/wrapper/MaxWidthWrapper";
import { AuthContext } from "../context/AuthContext";

interface AppRouterContext {
    auth: AuthContext;
}

export const Route = createRootRouteWithContext<AppRouterContext>()({
    component: () => (
        <React.Fragment>
            <Header></Header>
            <MaxWidthWrapper className="flex w-full ">
                <Navbar></Navbar>
                <div className="flex-1">
                    <Outlet />
                </div>
            </MaxWidthWrapper>
        </React.Fragment>
    ),
});
