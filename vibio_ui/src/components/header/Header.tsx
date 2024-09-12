import React from "react";
import MaxWidthWrapper from "../wrapper/MaxWidthWrapper";
import Logo from "../ui/logo";
import GlobalSearch from "./GolbalSearch";
import Notification from "./Notification";
import Profile from "./Profile";
import { Button } from "../ui/button";
import { useNavigate, useRouter } from "@tanstack/react-router";

type Props = {};

const Header = (props: Props) => {
    const navigation = useNavigate();
    return (
        <div className="border-b border-b-slate-200 bg-white/20 backdrop-blur-md top-0 sticky">
            <MaxWidthWrapper className="py-4 flex justify-between items-center gap-3">
                <Logo></Logo>
                <GlobalSearch></GlobalSearch>
                <div className="flex justify-end items-center gap-3">
                    <Notification></Notification>
                    {/* <Profile></Profile> */}
                    <Button onClick={() => navigation({ to: "/auth/login" })}>
                        Đăng nhập
                    </Button>
                </div>
            </MaxWidthWrapper>
        </div>
    );
};

export default Header;
