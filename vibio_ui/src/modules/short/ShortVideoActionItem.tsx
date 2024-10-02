import { ReactNode } from "@tanstack/react-router";
import { Button } from "../../components/ui/button";

type Props = {
    icon: ReactNode;
    label?: string;
};

const ShortVideoActionItem = ({ icon, label }: Props) => {
    return (
        <div className="flex flex-col justify-center items-center gap-1">
            <Button
                className="rounded-full size-[45px] p-0"
                variant="secondary"
            >
                {icon}
            </Button>
            {label && <p className="text-xs font-semibold">{label}</p>}
        </div>
    );
};

export default ShortVideoActionItem;
