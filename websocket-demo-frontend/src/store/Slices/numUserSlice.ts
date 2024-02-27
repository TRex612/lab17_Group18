import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {RootState} from "../store.ts";
interface numUserState {
numUser: number;
}

const initialState: numUserState = {
numUser: 0,
};

export const numUserSlice = createSlice({
name: 'numUser',
initialState,
reducers: {
setNumUser: (state, action: PayloadAction<number>) => {
            state.numUser = action.payload;
        },
    },
});

export const {setNumUser} = numUserSlice.actions;
export default numUserSlice.reducer;
export const selectNumUser = (state: RootState) => state.numUser.numUser;