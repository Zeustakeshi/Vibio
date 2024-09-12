import { z } from "zod";

export const registerSchema = z.object({
    email: z
        .string({ required_error: "Email không được bỏ trống" })
        .email({ message: "Email không đúng định dạng" }),
    username: z
        .string({
            required_error: "Tên người dùng không được bỏ trống",
        })
        .min(5, { message: "Tên người dùng quá ngắn" })
        .max(50, { message: "Tên người dùng quá dài" }),
    password: z
        .string({ required_error: "Mật khẩu không được bỏ trống" })
        .min(8, "Mật khẩu phải có ít nhất 8 ký tự")
        .regex(
            /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/,
            "Mật khẩu phải có: A-Z, a-z, 0-9, và ký tự đặc biệt"
        ),
});

export const loginSchema = z.object({
    email: z
        .string({ required_error: "Email không được bỏ trống" })
        .email({ message: "Email không đúng định dạng" }),
    password: z
        .string({ required_error: "Mật khẩu không được bỏ trống" })
        .min(8, { message: "Mật khẩu quá ngắn" }),
});

export const otpSchema = z.object({
    code: z.string({ required_error: "Không tìm thấy OTP code" }),
    otp: z
        .string({ required_error: "OTP không được bỏ trống" })
        .min(6, { message: "OTP phải có 6 kí tự" }),
});
