import { Button } from "@/components/ui/button";
import { useFormContext } from "react-hook-form";

type Props = {
    loading: boolean;
};

const FormHeader = ({ loading }: Props) => {
    const form = useFormContext();

    return (
        <div className="w-full flex justify-between items-center gap-2 top-[54px] sticky bg-white mb-4">
            <div>
                <h1 className="text-xl font-semibold py-3">Chi tiết video</h1>
            </div>
            <div className="flex justify-end items-center gap-2">
                <Button disabled={loading} type="submit">
                    {loading ? "Đang lưu" : "Lưu"}
                </Button>
            </div>
        </div>
    );
};

export default FormHeader;
