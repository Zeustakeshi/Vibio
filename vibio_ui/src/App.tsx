// Import the generated route tree

import { createRouter, RouterProvider } from "@tanstack/react-router";
import { useAuth } from "./context/AuthContext";
import { routeTree } from "./routeTree.gen";

// Create a new router instance
const router = createRouter({ routeTree, context: { auth: undefined! } });

// Register the router instance for type safety
declare module "@tanstack/react-router" {
    interface Register {
        router: typeof router;
    }
}

const App = () => {
    const auth = useAuth();
    return <RouterProvider router={router} context={{ auth: auth }} />;
};

export default App;
