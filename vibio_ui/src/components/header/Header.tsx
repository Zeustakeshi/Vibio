import { useNavigate } from "@tanstack/react-router";
import { TbMenu2 } from "react-icons/tb";
import { useDispatch } from "react-redux";
import { useAuth } from "../../context/AuthContext";
import GlobalSearch from "../../modules/search/GlobalSearch";
import { toggleNav } from "../../store/features/app/appSlice";
import { Button } from "../ui/button";
import Logo from "../ui/logo";
import MaxWidthWrapper from "../wrapper/MaxWidthWrapper";
import Notification from "./Notification";
import Profile from "./Profile";

type Props = {};

const Header = (props: Props) => {
    const navigation = useNavigate();
    const { isAuthenticated } = useAuth();
    const dispatch = useDispatch();
    return (
        <div className=" bg-white/90 h-[60px] z-20 backdrop-blur-md top-0 sticky">
            <MaxWidthWrapper className="py-3 flex justify-between items-center gap-3">
                <div className="flex justify-start items-center gap-3">
                    <Button
                        onClick={() => dispatch(toggleNav())}
                        variant="ghost"
                    >
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
