import { createSlice } from "@reduxjs/toolkit";

export interface AppState {
    navState: "open" | "haft open" | "close";
}

const initialState: AppState = {
    navState: "open",
};

export const AppSlice = createSlice({
    name: "app",
    initialState,
    reducers: {},
});

// Action creators are generated for each case reducer function
export const {} = AppSlice.actions;

export default AppSlice.reducer;
