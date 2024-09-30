import { useNavigate } from "@tanstack/react-router";
import { TbMenu2 } from "react-icons/tb";
import { useAuth } from "../../context/AuthContext";
import { Button } from "../ui/button";
import Logo from "../ui/logo";
import MaxWidthWrapper from "../wrapper/MaxWidthWrapper";
import GlobalSearch from "./GolbalSearch";
import Notification from "./Notification";
import Profile from "./Profile";

type Props = {};

const Header = (props: Props) => {
    const navigation = useNavigate();
    const { isAuthenticated } = useAuth();
    return (
        <div className=" bg-white/20 backdrop-blur-md top-0 sticky">
            <MaxWidthWrapper className="py-3 flex justify-between items-center gap-3">
                <div className="flex justify-start items-center gap-3">
                    <Button variant="ghost">
                        <TbMenu2 size={20} />
                    </Button>
                    <Logo></Logo>
                </div>
                <GlobalSearch></GlobalSearch>
                <div className="flex justify-end items-center gap-3">
                    <Notification></Notification>
                    {isAuthenticated && <Profile></Profile>}
                    {!isAuthenticated && (
                        <Button
                            onClick={() => navigation({ to: "/auth/login" })}
                        >
                            Đăng nhập
                        </Button>
                    )}
                </div>
            </MaxWidthWrapper>
        </div>
    );
};

export default Header;
