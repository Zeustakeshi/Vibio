import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { createFileRoute, Link, useNavigate } from "@tanstack/react-router";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { basicLogin } from "../../api/auth";
import { Button } from "../../components/ui/button";
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from "../../components/ui/form";
import { Input } from "../../components/ui/input";
import { useAuth } from "../../context/AuthContext";
import { useToast } from "../../hooks/use-toast";
import { loginSchema } from "../../schema/auth.schema";
export const Route = createFileRoute("/auth/_auth/login")({
    component: Login,
});

function Login() {
    const form = useForm<z.infer<typeof loginSchema>>({
        resolver: zodResolver(loginSchema),
    });

    const { toast } = useToast();
    const navigation = useNavigate();
    const { login } = useAuth();

    const { isPending, mutateAsync } = useMutation({
        mutationFn: (data: z.infer<typeof loginSchema>) => basicLogin(data),
        mutationKey: ["login"],
    });

    const onSubmit = async (value: z.infer<typeof loginSchema>) => {
        try {
            const data: any = await mutateAsync(value);
            login(data);
            if (data.code) {
                navigation({
                    to: "/auth/otp/mfa",
                    search: { code: data.code },
                });
            }
        } catch (error: any) {
            toast({
                title: "Đăng nhập thất bại",
                description: error,
            });
        }
    };

    return (
        <div className="w-full">
            <h1 className="w-full text-center my-5 text-2xl font-semibold">
                Đăng nhập
            </h1>
            <Form {...form}>
                <form
                    className="w-full space-y-3"
                    onSubmit={form.handleSubmit(onSubmit)}
                >
                    {/* Email */}
                    <FormField
                        control={form.control}
                        name="email"
                        render={({ field }) => (
                            <FormItem className="w-full">
                                <FormLabel>Địa chỉ email</FormLabel>
                                <FormControl {...form.control}>
                                    <Input
                                        className="w-full"
                                        placeholder="Địa chỉ email của bạn"
                                        {...field}
                                    ></Input>
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />
                    {/* Password */}
                    <FormField
                        control={form.control}
                        name="password"
                        render={({ field }) => (
                            <FormItem className="w-full">
                                <FormLabel>Mật khẩu</FormLabel>
                                <FormControl {...form.control}>
                                    <Input
                                        type="password"
                                        className="w-full"
                                        placeholder="•••••••••••"
                                        {...field}
                                    ></Input>
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />
                    <div className="w-full flex justify-end">
                        <Link to="/" className="text-sm underline text-primary">
                            Bạn quên mật khẩu?
                        </Link>
                    </div>
                    <Button
                        type="submit"
                        className="w-full"
                        disabled={isPending}
                    >
                        {isPending ? "Đang xử lý" : "Đăng nhập"}
                    </Button>
                </form>
                <div className="w-full flex justify-center my-3 text-sm space-x-2">
                    <p>Bạn chưa có tài khoản? </p>
                    <Link
                        to="/auth/register"
                        className="text-primary underline"
                    >
                        Tạo tài khoản
                    </Link>
                </div>
            </Form>
        </div>
    );
}
