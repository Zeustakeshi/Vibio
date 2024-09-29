import { IoSearch } from "react-icons/io5";

type Props = {};

const GlobalSearch = (props: Props) => {
    return (
        <div className="flex items-center gap-1 min-w-[500px] rounded-full overflow-hidden bg-slate-100 dark:bg-slate-900">
            <div className="p-3 ">
                <IoSearch size={16} />
            </div>
            <input
                placeholder="Tìm kiếm"
                className="text-sm flex-1 !border-none !outline-none bg-transparent pr-2 pl-0 py-1"
            ></input>
        </div>
    );
};

export default GlobalSearch;
