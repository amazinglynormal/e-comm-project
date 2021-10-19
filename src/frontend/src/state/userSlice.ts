import { createSlice } from "@reduxjs/toolkit";
import Order from "../interfaces/order.interface";
import userLogin from "./async-thunks/userLogin";
import userSignUp from "./async-thunks/userSignUp";
import { RootState } from "./store";

interface UserState {
  id: string | undefined;
  username: string | undefined;
  email: string | undefined;
  role: "GUEST" | "CUSTOMER";
  orders: Order[];
  token: string | undefined;
  error: string | null;
  status: "idle" | "pending" | "succeeded" | "failed";
}

const initialState: UserState = {
  id: undefined,
  username: undefined,
  email: undefined,
  role: "GUEST",
  orders: [],
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
      state.id = action.payload.id;
      state.username = action.payload.username;
      state.email = action.payload.email;
      state.role = action.payload.role;
      state.orders = action.payload.orders;
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
      state.id = action.payload.id;
      state.username = action.payload.username;
      state.email = action.payload.email;
      state.role = action.payload.role;
      state.orders = action.payload.orders;
      state.error = null;
      state.status = "succeeded";
    });
  },
});

export const selectUser = (state: RootState) => state.user;

export const selectUserOrders = (state: RootState) => state.user.orders;

export const selectUserOrderById = (state: RootState, id: number) => {
  return state.user.orders.find((order) => order.id === id);
};

export const selectUsername = (state: RootState) => state.user.username;

export default userSlice.reducer;
