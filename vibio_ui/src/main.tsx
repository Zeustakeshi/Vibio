import { StrictMode } from "react";
import { createRoot } from "react-dom/client";

import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { Provider } from "react-redux";
import App from "./App";
import { Toaster } from "./components/ui/toaster";
import { AuthProvider } from "./context/AuthContext";
import "./index.css";
import { store } from "./store/store";
// Create a client
const queryClient = new QueryClient();

createRoot(document.getElementById("root")!).render(
    <StrictMode>
        <Provider store={store}>
            <QueryClientProvider client={queryClient}>
                <AuthProvider>
                    <App />
                </AuthProvider>
                <ReactQueryDevtools initialIsOpen={false} position="bottom" />
                <Toaster />
            </QueryClientProvider>
        </Provider>
    </StrictMode>
);
