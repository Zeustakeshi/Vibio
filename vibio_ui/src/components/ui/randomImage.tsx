import { randomIllustration } from "../../lib/randomIllustration";
import { cn } from "../../lib/utils";

type Props = {
    className?: string;
};

const RandomImage = ({ className }: Props) => {
    return (
        <div className={cn(" rounded-xl overflow-hidden", className)}>
            <img
                className="w-full h-full object-cover"
                src={randomIllustration()}
                alt="vibio-image"
            />
        </div>
    );
};

export default RandomImage;
