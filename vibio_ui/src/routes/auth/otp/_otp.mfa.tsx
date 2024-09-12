import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/auth/otp/_otp/mfa")({
    component: () => <div>Hello /auth/otp/_otp/mfa!</div>,
});
