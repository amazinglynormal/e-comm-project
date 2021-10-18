import { createSlice } from "@reduxjs/toolkit";
import User from "../interfaces/user.interface";
import { RootState } from "./store";

const initialState: User = {
  id: "",
  username: "",
  email: "",
  role: "",
  orders: [],
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
