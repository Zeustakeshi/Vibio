import { IoSearch } from "react-icons/io5";
import { Button } from "../ui/button";
import { Input } from "../ui/input";

type Props = {};

const GlobalSearch = (props: Props) => {
    return (
        <div className="flex">
            <Input
                className="border   rounded-full rounded-tr-none rounded-br-none min-w-[400px] px-5 py-2"
                placeholder="Tìm kiếm"
            ></Input>
            <Button className="rounded-full rounded-tl-none rounded-bl-none ">
                <IoSearch size={20} />
            </Button>
        </div>
    );
};

export default GlobalSearch;
