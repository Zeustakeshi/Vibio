import { createFileRoute, Outlet } from "@tanstack/react-router";
import OauthLogin from "../../components/auth/OauthLogin";

export const Route = createFileRoute("/auth/_auth")({
    component: () => (
        <div className="min-h-[90svh] flex justify-center items-center">
            <div className="shadow-xl p-5 border rounded-xl min-w-[400px] min-h-[500px] flex flex-col justify-start items-center">
                <div className="flex-1 w-full">
                    <Outlet></Outlet>
                </div>
                <div className="mb-10 w-full h-full justify-center items-center flex flex-col">
                    <div className="flex justify-center items-center gap-3 w-[80%] my-3">
                        <span className="h-[1px] bg-slate-200 flex-1"></span>
                        <span className="text-muted-foreground font-medium text-sm">
                            Hoặc{" "}
                        </span>
                        <span className="h-[1px] bg-slate-200 flex-1"></span>
                    </div>
                    <OauthLogin></OauthLogin>
                </div>
            </div>
        </div>
    ),
});
