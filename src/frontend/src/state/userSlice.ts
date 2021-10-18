import { createSlice } from "@reduxjs/toolkit";
import Order from "../interfaces/order.interface";
import { RootState } from "./store";

interface UserState {
  id: string | undefined;
  username: string | undefined;
  email: string | undefined;
  role: "GUEST" | "CUSTOMER";
  orders: Order[];
  token: string | undefined;
}

const initialState: UserState = {
  id: undefined,
  username: undefined,
  email: undefined,
  role: "GUEST",
  orders: [],
  token: undefined,
};

export const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {},
});

export const selectUser = (state: RootState) => state.user;

export const selectUserOrders = (state: RootState) => state.user.orders;

export const selectUserOrderById = (state: RootState, id: number) => {
  return state.user.orders.find((order) => order.id === id);
};

export const selectUsername = (state: RootState) => state.user.username;

export default userSlice.reducer;
