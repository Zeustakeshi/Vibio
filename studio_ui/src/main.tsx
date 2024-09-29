import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { Provider } from "react-redux";
import App from "./App.tsx";
import { AuthProvider } from "./context/AuthContext.tsx";
import { ThemeProvider } from "./context/ThemeContext.tsx";
import "./index.css";
import { store } from "./store/store.ts";

// Create a client
const queryClient = new QueryClient();

createRoot(document.getElementById("root")!).render(
    <StrictMode>
        <QueryClientProvider client={queryClient}>
            <ThemeProvider defaultTheme="light" storageKey="vite-ui-theme">
                <Provider store={store}>
                    <AuthProvider>
                        <App />
                    </AuthProvider>
                </Provider>
            </ThemeProvider>
            <ReactQueryDevtools></ReactQueryDevtools>
        </QueryClientProvider>
    </StrictMode>
);
