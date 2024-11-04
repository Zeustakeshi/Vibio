import { IoSearch } from "react-icons/io5";

type Props = {
    value: string;
};

const SearchItem = ({ value }: Props) => {
    return (
        <div className="px-5 py-3 cursor-pointer hover:bg-slate-100 flex justify-start items-center gap-5">
            <span className="text-muted-foreground">
                <IoSearch />
            </span>
            <p className="line-clamp-1 text-sm">{value}</p>
        </div>
    );
};

export default SearchItem;
