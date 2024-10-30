import { createSlice } from "@reduxjs/toolkit";
import { RootState } from "../../store";

// Define a type for the slice state
export type NavState = "open" | "half-open" | "hidden";

interface AppState {
    navState: NavState;
}

// Define the initial state using that type
const initialState: AppState = {
    navState: "open",
};

export const appSlice = createSlice({
    name: "counter",
    // `createSlice` will infer the state type from the `initialState` argument
    initialState,
    reducers: {
        toggleNav(state) {
            const { navState } = state;
            if (navState === "open") state.navState = "half-open";
            else if (navState === "half-open") state.navState = "open";
            return state;
        },
    },
});

export const { toggleNav } = appSlice.actions;

// Other code such as selectors can use the imported `RootState` type
export const selectApp = (state: RootState) => state.app;

export default appSlice.reducer;
