import { createSlice } from "@reduxjs/toolkit";
import User from "../interfaces/user.interface";
import userLogin from "./async-thunks/userLogin";
import userSignUp from "./async-thunks/userSignUp";
import userReauth from "./async-thunks/userReauth";
import updateUserInfo from "./async-thunks/updateUserInfo";
import { RootState } from "./store";
import deleteUserAccount from "./async-thunks/deleteUserAccount";

interface UserState {
  user: User | undefined | null;
  token: string | undefined;
  error: string | null;
  status: "idle" | "pending" | "succeeded" | "failed";
}

const initialState: UserState = {
  user: undefined,
  token: undefined,
  status: "idle",
  error: null,
};

export const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(userLogin.rejected, (state, action) => {
      state.status = "failed";
      if (action.error.message) {
        state.error = action.error.message;
      }
    });

    builder.addCase(userLogin.fulfilled, (state, action) => {
      state.user = action.payload.user;
      state.token = action.payload.token;
      state.error = null;
      state.status = "succeeded";
    });

    builder.addCase(userSignUp.rejected, (state, action) => {
      state.status = "failed";
      if (action.error.message) {
        state.error = action.error.message;
      }
    });

    builder.addCase(userSignUp.fulfilled, (state, action) => {
      state.user = action.payload;
      state.error = null;
      state.status = "succeeded";
    });

    builder.addCase(userReauth.rejected, (state, action) => {
      state.status = "failed";
      if (action.error.message) {
        state.error = action.error.message;
      }
    });

    builder.addCase(updateUserInfo.fulfilled, (state, action) => {
      state.user = action.payload;
      state.error = null;
      state.status = "succeeded";
    });
    builder.addCase(updateUserInfo.rejected, (state, action) => {
      state.status = "failed";
      if (action.error.message) {
        state.error = action.error.message;
      }
    });

    builder.addCase(deleteUserAccount.fulfilled, (state) => {
      state.status = "succeeded";
      state.token = "";
      state.user = null;
    });

    builder.addCase(deleteUserAccount.rejected, (state, action) => {
      state.status = "failed";
      if (action.error.message) {
        state.error = action.error.message;
      }
    });
  },
});

export const selectUser = (state: RootState) => state.user.user;

export const selectUserOrders = (state: RootState) => state.user.user?.orders;

export const selectUserOrderById = (state: RootState, id: number) => {
  return state.user.user?.orders.find((order) => order.id === id);
};

export const selectUsername = (state: RootState) => state.user.user?.username;

export default userSlice.reducer;
