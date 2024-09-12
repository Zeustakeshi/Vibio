// Import the generated route tree
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { createRouter, RouterProvider } from "@tanstack/react-router";
import { Toaster } from "./components/ui/toaster";
import { routeTree } from "./routeTree.gen";

// Create a new router instance
const router = createRouter({ routeTree });

// Register the router instance for type safety
declare module "@tanstack/react-router" {
    interface Register {
        router: typeof router;
    }
}

// Create a client
const queryClient = new QueryClient();

const App = () => {
    return (
        <QueryClientProvider client={queryClient}>
            <RouterProvider router={router} />;
            <ReactQueryDevtools initialIsOpen={false} position="bottom" />
            <Toaster />
        </QueryClientProvider>
    );
};

export default App;
