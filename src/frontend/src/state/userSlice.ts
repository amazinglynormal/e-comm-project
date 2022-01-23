import { createSlice } from "@reduxjs/toolkit";
import { RootState } from "./store";

import User from "../interfaces/user.interface";

import deleteUserAccount from "./async-thunks/deleteUserAccount";
import updateUserInfo from "./async-thunks/updateUserInfo";
import userSignUp from "./async-thunks/userSignUp";
import userReauth from "./async-thunks/userReauth";
import userLogout from "./async-thunks/userLogout";
import userLogin from "./async-thunks/userLogin";
import refresh from "./async-thunks/refresh";

interface UserState {
  user: User | undefined | null;
  error: string | null;
  status: "idle" | "pending" | "succeeded" | "failed";
}

const initialState: UserState = {
  user: undefined,
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
      state.user = null;
    });

    builder.addCase(deleteUserAccount.rejected, (state, action) => {
      state.status = "failed";
      if (action.error.message) {
        state.error = action.error.message;
      }
    });

    builder.addCase(refresh.fulfilled, (state, action) => {
      state.user = action.payload.user;
      state.status = "succeeded";
      state.error = null;
    });

    builder.addCase(refresh.rejected, (state, action) => {
      state.status = "failed";
      if (action.error.message) {
        state.error = action.error.message;
      }
    });

    builder.addCase(userLogout.fulfilled, (state) => {
      state.user = null;
      state.status = "idle";
      state.error = null;
      sessionStorage.clear();
      localStorage.clear();
    });

    builder.addCase(userLogout.rejected, (state, action) => {
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
