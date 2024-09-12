import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation } from "@tanstack/react-query";
import { createFileRoute, Link, useNavigate } from "@tanstack/react-router";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { register } from "../../api/auth";
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
import { useToast } from "../../hooks/use-toast";
import { registerSchema } from "../../schema/auth.schema";

export const Route = createFileRoute("/auth/_auth/register")({
    component: Register,
});

function Register() {
    const form = useForm<z.infer<typeof registerSchema>>({
        resolver: zodResolver(registerSchema),
    });

    const { isPending, mutateAsync } = useMutation({
        mutationFn: (data: z.infer<typeof registerSchema>) => register(data),
        mutationKey: ["register"],
    });

    const navigation = useNavigate();
    const { toast } = useToast();
    const onSubmit = async (value: z.infer<typeof registerSchema>) => {
        try {
            const data: any = await mutateAsync(value);
            navigation({
                to: "/auth/otp/new-account",
                search: { code: data.code },
            });
        } catch (error: any) {
            if (!error.email && !error.username && !error.password) {
                toast({
                    title: "Tạo tài khoản thất bại",
                    description: error,
                });
                return;
            }
            if (error.email) form.setError("email", { message: error.email });
            if (error.username)
                form.setError("username", { message: error.username });
            if (error.password)
                form.setError("password", { message: error.password });
        }
    };

    return (
        <div className="w-full">
            <h1 className="w-full text-center my-5 text-2xl font-semibold">
                Tạo tài khoản
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
                    {/* Usrename */}
                    <FormField
                        control={form.control}
                        name="username"
                        render={({ field }) => (
                            <FormItem className="w-full">
                                <FormLabel>Tên tài khoản</FormLabel>
                                <FormControl {...form.control}>
                                    <Input
                                        className="w-full"
                                        placeholder="Mọi người có thể gọi bạn là gì?    "
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

                    <Button
                        type="submit"
                        className="w-full"
                        disabled={isPending}
                    >
                        {isPending ? "Đang xử lý" : "Tạo tài khoản"}
                    </Button>
                </form>
                <div className="w-full flex justify-center my-3 text-sm space-x-2">
                    <p>Bạn đã là thành viên? </p>
                    <Link to="/auth/login" className="text-primary underline">
                        Đăng nhập
                    </Link>
                </div>
            </Form>
        </div>
    );
}
