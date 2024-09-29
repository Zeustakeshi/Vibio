import { LuMenu } from "react-icons/lu";
import Notification from "../notification/Notification";
import GlobalSearch from "../search/GlobalSearch";
import { Button } from "../ui/button";
import Logo from "../ui/logo";
import MaxWidthWrapper from "../warpper/MaxWidthWrapper";
import NewContentTrigger from "./new-content/NewContentTrigger";
import Profile from "./Profile";

type Props = {};

const Header = (props: Props) => {
    return (
        <div className="w-full bg-white dark:bg-slate-800 backdrop-blur-md top-0 z-20  sticky border-b border-b-slate-200 dark:border-slate-600">
            <MaxWidthWrapper className="px-4 py-2 flex justify-between items-center gap-2">
                <div className="flex justify-start items-center gap-2">
                    <Button size="sm" variant="ghost">
                        <LuMenu size={16} />
                    </Button>
                    <Logo></Logo>
                </div>
                <GlobalSearch></GlobalSearch>
                <div className="flex justify-end items-center gap-2">
                    <NewContentTrigger></NewContentTrigger>
                    <Notification></Notification>
                    <Profile></Profile>
                </div>
            </MaxWidthWrapper>
        </div>
    );
};

export default Header;
