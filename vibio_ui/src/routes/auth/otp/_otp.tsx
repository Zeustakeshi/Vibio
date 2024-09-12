import { createFileRoute, Outlet } from "@tanstack/react-router";
import { z } from "zod";

const otpSearchParamSchema = z.object({
    code: z.string(),
});

export const Route = createFileRoute("/auth/otp/_otp")({
    validateSearch: (search) => otpSearchParamSchema.parse(search),
    component: () => (
        <div className="min-h-[90svh] flex justify-center items-center">
            <div className="min-w-[400px] min-h-[300px] shadow-xl rounded-lg p-5">
                <Outlet />
            </div>
        </div>
    ),
});
