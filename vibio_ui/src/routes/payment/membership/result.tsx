import { useQuery } from "@tanstack/react-query";
import { createFileRoute, useNavigate } from "@tanstack/react-router";
import { z } from "zod";
import { validateMemberShipPayment } from "../../../api/payment";

const paymentSearchParamSchema = z.object({
    orderId: z.string(),
});

export const Route = createFileRoute("/payment/membership/result")({
    validateSearch: paymentSearchParamSchema,
    beforeLoad: ({ search }) => {
        try {
            paymentSearchParamSchema.parse(search);
        } catch (error: any) {
            window.location.href = window.location.origin;
        }
    },
    component: PaymentResult,
});

function PaymentResult() {
    const { orderId } = Route.useSearch();

    const { isLoading } = useQuery({
        queryKey: ["validate-payment", orderId],
        queryFn: () => validateMemberShipPayment(orderId),
    });
    const navigation = useNavigate();

    if (!isLoading) {
        navigation({
            to: "/",
        });
        return;
    }

    return (
        <div className="w-full h-full flex justify-center items-center flex-col gap-y-5">
            <div className="size-[50px] border-4 border-primary rounded-full animate-spin border-t-transparent"></div>
            <p className="text-muted-foreground">Đang kiểm tra</p>
        </div>
    );
}
