import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { createFileRoute, redirect } from "@tanstack/react-router";
import { useForm } from "react-hook-form";
import { MdOutlineMailOutline } from "react-icons/md";
import { z } from "zod";
import {
    resendCreateAccountOtp,
    verifyCreateAccountOtp,
} from "../../../api/auth";
import { Button } from "../../../components/ui/button";
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormMessage,
} from "../../../components/ui/form";
import {
    InputOTP,
    InputOTPGroup,
    InputOTPSlot,
} from "../../../components/ui/input-otp";
import { useAuth } from "../../../context/AuthContext";
import { useToast } from "../../../hooks/use-toast";
import { otpSchema } from "../../../schema/auth.schema";

export const Route = createFileRoute("/auth/otp/_otp/new-account")({
    beforeLoad: ({ search }) => {
        if (!search.code)
            throw redirect({
                to: "/auth/login",
            });
    },
    component: CreateAccountOtp,
});

function CreateAccountOtp() {
    const { code } = Route.useSearch();
    const form = useForm<z.infer<typeof otpSchema>>({
        defaultValues: {
            code: code,
        },
        resolver: zodResolver(otpSchema),
    });

    const { login } = useAuth();
    const { toast } = useToast();

    const verifyOtpMutation = useMutation({
        mutationFn: (data: z.infer<typeof otpSchema>) =>
            verifyCreateAccountOtp(data),
        mutationKey: ["validate", "otp", "create-account"],
    });

    const resendOtpMutation = useMutation({
        mutationFn: () => resendCreateAccountOtp(code),
        mutationKey: ["resend", "otp", "create account"],
    });

    const onSubmit = async (value: z.infer<typeof otpSchema>) => {
        try {
            const data: any = await verifyOtpMutation.mutateAsync(value);
            login(data);
        } catch (error: any) {
            toast({
                title: "Xác thực thất bại",
                description: error,
            });
        }
    };

    const handleResendOtp = async () => {
        try {
            await resendOtpMutation.mutateAsync();
            toast({
                title: "Gửi lại OTP thành công",
                description: "Chúng tôi đã gửi OTP đến địa chỉ email của bạn.",
            });
        } catch (error: any) {
            toast({
                title: "Gửi lại OTP thất bại",
                description: error,
            });
        }
    };

    return (
        <div className="w-full h-full flex flex-col justify-center items-center">
            <div className="p-2 border rounded-md">
                <MdOutlineMailOutline size={20}></MdOutlineMailOutline>
            </div>
            <div className="my-5">
                <h1 className="mb-2 w-full text-center font-semibold text-lg">
                    Xác thực OTP
                </h1>
                <p className="text-sm text-muted-foreground">
                    Hoàn tất xác thực OTP để tạo tài khoản
                </p>
            </div>
            <div className="w-full">
                <Form {...form}>
                    <form onSubmit={form.handleSubmit(onSubmit)}>
                        <FormField
                            control={form.control}
                            name="otp"
                            render={({ field }) => (
                                <FormItem className="w-full mb-4 flex flex-col items-center  ">
                                    <FormControl>
                                        <InputOTP maxLength={6} {...field}>
                                            <InputOTPGroup>
                                                <InputOTPSlot index={0} />
                                                <InputOTPSlot index={1} />
                                                <InputOTPSlot index={2} />
                                                <InputOTPSlot index={3} />
                                                <InputOTPSlot index={4} />
                                                <InputOTPSlot index={5} />
                                            </InputOTPGroup>
                                        </InputOTP>
                                    </FormControl>
                                    <FormMessage />
                                </FormItem>
                            )}
                        />
                        <Button
                            disabled={
                                verifyOtpMutation.isPending ||
                                resendOtpMutation.isPending
                            }
                            type="submit"
                            className="w-full my-2"
                        >
                            {verifyOtpMutation.isPending
                                ? "Đang xử lý"
                                : "Xác nhận"}
                        </Button>
                    </form>
                </Form>
            </div>
            <div className="flex w-full justify-center items-center gap-x-1 text-sm">
                <p className="text-muted-foreground">
                    Bạn chưa nhận được email?
                </p>
                <Button
                    onClick={handleResendOtp}
                    disabled={
                        verifyOtpMutation.isPending ||
                        resendOtpMutation.isPending
                    }
                    variant="link"
                    className="!mx-0 !px-0"
                >
                    Gửi lại
                </Button>
            </div>
        </div>
    );
}
