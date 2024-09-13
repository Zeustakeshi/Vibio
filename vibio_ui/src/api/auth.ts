import { z } from "zod";
import { api } from "../lib/api";
import { loginSchema, otpSchema } from "../schema/auth.schema";

export const register = async (data: z.infer<typeof loginSchema>) => {
    return await api.post("/auth/register", data);
};

export const basicLogin = async (data: z.infer<typeof loginSchema>) => {
    return await api.post("/auth/login", data);
};

export const verifyCreateAccountOtp = async (
    data: z.infer<typeof otpSchema>
) => {
    return await api.post("/auth/new-account/verify-otp", data);
};

export const resendCreateAccountOtp = async (code: string) => {
    return await api.post("/auth/new-account/resend-otp", { code });
};

export const verifyMfaOtp = async (data: z.infer<typeof otpSchema>) => {
    return await api.post("/auth/mfa/verify-otp", data);
};

export const resendMfaOtp = async (code: string) => {
    return await api.post("/auth/mfa/resend-otp", { code });
};
