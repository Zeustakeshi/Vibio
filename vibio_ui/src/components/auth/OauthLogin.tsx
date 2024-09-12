import { BsGithub } from "react-icons/bs";
import { FcGoogle } from "react-icons/fc";
import { Button } from "../ui/button";
type Props = {};

const OauthLogin = (props: Props) => {
    return (
        <div className="flex justify-center items-center gap-5">
            <Button
                variant="outline"
                className="flex-1 flex justify-center items-center gap-3"
            >
                <FcGoogle size={20}></FcGoogle>
                <span>Tiếp tục với Google</span>
            </Button>
            <Button
                variant="outline"
                className="flex-1 flex justify-center items-center gap-3"
            >
                <BsGithub size={20}></BsGithub>
                <span>Tiếp tục với Github</span>
            </Button>
        </div>
    );
};

export default OauthLogin;
