import { getAllPlaylistByChannelId } from "@/api/playlist";
import { buttonVariants } from "@/components/ui/button";
import { DataTable } from "@/components/ui/data-table";
import {
    Pagination,
    PaginationContent,
    PaginationItem,
} from "@/components/ui/pagination";
import { useAuth } from "@/context/AuthContext";
import { cn } from "@/lib/utils";
import { columns } from "@/modules/content/playlist/columns";
import { keepPreviousData, useQuery } from "@tanstack/react-query";
import { createFileRoute, Link } from "@tanstack/react-router";

export const Route = createFileRoute("/_layout/content/_content/playlists")({
    validateSearch: (search: Record<string, unknown>) => {
        const page = search?.page ?? 1;
        const limit = search?.limit ?? DEFAULT_LIMIT;

        return {
            page: Math.max(0, Number(page)),
            limit: Math.max(1, Number(limit)),
        };
    },
    component: Playlists,
});

const DEFAULT_LIMIT = 5;

function Playlists() {
    const { limit, page } = Route.useSearch();

    const { isAuthenticated } = useAuth();

    const { data, isLoading } = useQuery({
        queryKey: ["playlists", page, limit],
        queryFn: () => getAllPlaylistByChannelId(page - 1, limit),
        placeholderData: keepPreviousData,
        enabled: isAuthenticated,
    });

    return (
        <div className="flex-1 min-w-full min-h-[300px] flex flex-col justify-center items-center">
            {isLoading && (
                <div className="size-[32px] rounded-full border-[3px] border-primary border-t-transparent animate-spin"></div>
            )}
            {!isLoading && (
                <DataTable
                    className="w-full"
                    columns={columns}
                    data={data?.content || []}
                />
            )}
            {!isLoading && data?.totalPages && data.totalPages > 0 && (
                <Pagination className="my-4">
                    <PaginationContent>
                        {page - 2 > 0 && (
                            <PaginationItem>
                                <Link
                                    className={cn(
                                        buttonVariants({
                                            variant: "ghost",
                                            size: "sm",
                                        })
                                    )}
                                    to="/content/playlists"
                                    search={() => ({
                                        page: page - 2,
                                        limit: DEFAULT_LIMIT,
                                    })}
                                >
                                    {page - 2}
                                </Link>
                            </PaginationItem>
                        )}

                        {page - 1 > 0 && (
                            <PaginationItem>
                                <Link
                                    className={cn(
                                        buttonVariants({
                                            variant: "ghost",
                                            size: "sm",
                                        })
                                    )}
                                    to="/content/playlists"
                                    search={() => ({
                                        page: page - 1,
                                        limit: DEFAULT_LIMIT,
                                    })}
                                >
                                    {page - 1}
                                </Link>
                            </PaginationItem>
                        )}

                        <PaginationItem>
                            <Link
                                className={cn(
                                    buttonVariants({
                                        variant: "default",
                                        size: "sm",
                                    })
                                )}
                                href={`#`}
                            >
                                {page}
                            </Link>
                        </PaginationItem>

                        {page + 1 <= data.totalPages && (
                            <PaginationItem>
                                <Link
                                    className={cn(
                                        buttonVariants({
                                            variant: "ghost",
                                            size: "sm",
                                        })
                                    )}
                                    to="/content/playlists"
                                    search={() => ({
                                        page: page + 1,
                                        limit: DEFAULT_LIMIT,
                                    })}
                                >
                                    {page + 1}
                                </Link>
                            </PaginationItem>
                        )}

                        {page + 2 <= data.totalPages && (
                            <PaginationItem>
                                <Link
                                    className={cn(
                                        buttonVariants({
                                            variant: "ghost",
                                            size: "sm",
                                        })
                                    )}
                                    to="/content/playlists"
                                    search={() => ({
                                        page: page + 2,
                                        limit: DEFAULT_LIMIT,
                                    })}
                                >
                                    {page + 2}
                                </Link>
                            </PaginationItem>
                        )}
                    </PaginationContent>
                </Pagination>
            )}
        </div>
    );
}
