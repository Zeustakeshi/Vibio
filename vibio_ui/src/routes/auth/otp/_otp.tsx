import { createFileRoute, Outlet, useNavigate } from "@tanstack/react-router";
import { useEffect } from "react";
import { z } from "zod";
import { useAuth } from "../../../context/AuthContext";

const otpSearchParamSchema = z.object({
    code: z.string(),
});

export const Route = createFileRoute("/auth/otp/_otp")({
    validateSearch: (search) => otpSearchParamSchema.parse(search),
    component: OtpLayout,
});

function OtpLayout() {
    const { isAuthenticated, authLoading } = useAuth();
    const navigation = useNavigate();

    useEffect(() => {
        if (authLoading) return;
        if (isAuthenticated) navigation({ to: "/" });
    }, [isAuthenticated, authLoading]);

    return (
        <div className="min-h-[90svh] flex justify-center items-center">
            <div className="min-w-[400px] min-h-[300px] shadow-xl rounded-lg p-5">
                <Outlet />
            </div>
        </div>
    );
}
