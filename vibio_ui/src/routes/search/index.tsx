import { createFileRoute } from "@tanstack/react-router";
import SearchFilter from "../../modules/search/SearchFilter";
import SearchResult from "../../modules/search/SearchResult";

export const Route = createFileRoute("/search/")({
    validateSearch: (search) => {
        return {
            query: search.query as string | undefined,
        };
    },
    component: SearchPage,
});

function SearchPage() {
    const { query } = Route.useSearch();
    return (
        <div className="p-5">
            <SearchFilter></SearchFilter>
            <SearchResult query={query}></SearchResult>
        </div>
    );
}
