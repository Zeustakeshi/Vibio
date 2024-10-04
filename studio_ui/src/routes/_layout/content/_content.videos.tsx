import { getAllVideo } from "@/api/video";
import { DataTable } from "@/components/ui/data-table";
import {
    Pagination,
    PaginationContent,
    PaginationItem,
    PaginationLink,
} from "@/components/ui/pagination";
import { columns } from "@/modules/content/video/columns";
import { useQuery } from "@tanstack/react-query";
import { createFileRoute } from "@tanstack/react-router";

const DEFAULT_LIMIT = 5;

export const Route = createFileRoute("/_layout/content/_content/videos")({
    validateSearch: (search: Record<string, unknown>) => {
        const page = search?.page ?? 1;
        const limit = search?.limit ?? DEFAULT_LIMIT;

        return {
            page: Math.max(0, Number(page)),
            limit: Math.max(1, Number(limit)),
        };
    },
    component: Videos,
});

function Videos() {
    const { limit, page } = Route.useSearch();

    const { data, isLoading } = useQuery({
        queryKey: ["videos"],
        queryFn: () => getAllVideo(page - 1, limit),
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
                                <PaginationLink
                                    href={`/content/videos?page=${page - 2}&limit=${DEFAULT_LIMIT}`}
                                >
                                    {page - 2}
                                </PaginationLink>
                            </PaginationItem>
                        )}

                        {page - 1 > 0 && (
                            <PaginationItem>
                                <PaginationLink
                                    href={`/content/videos?page=${page - 1}&limit=${DEFAULT_LIMIT}`}
                                >
                                    {page - 1}
                                </PaginationLink>
                            </PaginationItem>
                        )}

                        <PaginationItem>
                            <PaginationLink
                                className="bg-primary text-white"
                                href={`#`}
                            >
                                {page}
                            </PaginationLink>
                        </PaginationItem>

                        {page + 1 <= data.totalPages && (
                            <PaginationItem>
                                <PaginationLink
                                    href={`/content/videos?page=${page + 1}&limit=${DEFAULT_LIMIT}`}
                                >
                                    {page + 1}
                                </PaginationLink>
                            </PaginationItem>
                        )}

                        {page + 2 <= data.totalPages && (
                            <PaginationItem>
                                <PaginationLink
                                    href={`/content/videos?page=${page + 2}&limit=${DEFAULT_LIMIT}`}
                                >
                                    {page + 2}
                                </PaginationLink>
                            </PaginationItem>
                        )}
                    </PaginationContent>
                </Pagination>
            )}
        </div>
    );
}
