import { useMutation } from "@tanstack/react-query";
import { Link, useNavigate } from "@tanstack/react-router";
import { useEffect, useState } from "react";
import { IoSearch } from "react-icons/io5";
import { searchVideoAutoComplete } from "../../api/search";
import { Button } from "../../components/ui/button";
import { Input } from "../../components/ui/input";
import useClickOutSide from "../../hooks/useClickOutSide";
import useDebounce from "../../hooks/userDebounce";
import { cn } from "../../lib/utils";
import VoiceSearch from "./VoiceSearch";

type Props = {};

const GlobalSearch = (props: Props) => {
    const [searchValue, setSearchValue] = useState<string>("");
    const [showSuggestion, setShowSuggestion] = useState<boolean>(false);
    const { nodeRef: searchRef } = useClickOutSide(() => {
        setShowSuggestion(false);
    });

    const searchValueDebounce = useDebounce(searchValue, 500);
    const navigation = useNavigate();
    const {
        data: autoCompleteValue,
        mutateAsync: autoCompleteMutation,
        isPending,
    } = useMutation({
        mutationKey: ["global-search-autocomplete", searchValueDebounce],
        mutationFn: (query: string) => searchVideoAutoComplete(query),
    });

    useEffect(() => {
        if (!searchValueDebounce.trim()) return;
        (async () => {
            try {
                await autoCompleteMutation(searchValueDebounce);
                setShowSuggestion(true);
            } catch (error: any) {
                console.log({ error });
            }
        })();
    }, [searchValueDebounce]);

    const handleSearch = (searchValue: string) => {
        navigation({
            to: "/search",
            search: {
                query: searchValue,
            },
        });
    };

    return (
        <div ref={searchRef} className="flex gap-x-5">
            <form
                className="flex relative group"
                onSubmit={(e) => {
                    e.preventDefault();
                    handleSearch(searchValue);
                }}
            >
                <Input
                    value={searchValue}
                    onChange={(e) => setSearchValue(e.target.value)}
                    className=" border rounded-full rounded-tr-none rounded-br-none min-w-[500px] px-5 py-2"
                    placeholder="Tìm kiếm"
                    onFocus={() => setShowSuggestion(true)}
                ></Input>
                <Button
                    type="submit"
                    className="rounded-full rounded-tl-none rounded-bl-none "
                >
                    <IoSearch size={20} />
                </Button>

                <div
                    className={cn(
                        "absolute top-[120%] left-0 w-full bg-white shadow-md rounded-md hidden max-h-[500px] overflow-y-scroll custom-scroll",
                        {
                            block: showSuggestion,
                        }
                    )}
                >
                    {autoCompleteValue?.map((value, index) => (
                        <Link
                            onClick={() => setShowSuggestion(false)}
                            to="/search"
                            search={{ query: value }}
                            resetScroll
                            replace
                            className="px-5 py-3 cursor-pointer hover:bg-slate-100 flex justify-start items-center gap-5"
                            key={index}
                        >
                            <span className="text-muted-foreground">
                                <IoSearch />
                            </span>
                            <p className="line-clamp-1 text-sm">{value}</p>
                        </Link>
                    ))}
                </div>
            </form>

            <VoiceSearch handleSearch={handleSearch}></VoiceSearch>
        </div>
    );
};

export default GlobalSearch;
