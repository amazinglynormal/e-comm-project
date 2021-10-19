import { createSlice } from "@reduxjs/toolkit";
import Product from "../interfaces/product.interface";
import type { RootState } from "./store";

interface OrderState {
  id: number | undefined;
  status: "INACTIVE" | "ACTIVE" | "DISPATCHED" | "DELIVERED";
  userId: string | undefined;
  products: Product[];
}

const initialState: OrderState = {
  id: undefined,
  status: "INACTIVE",
  userId: undefined,
  products: [],
};

export const orderSlice = createSlice({
  name: "order",
  initialState,
  reducers: {},
});

export const selectOrder = (state: RootState) => state.order;

export const selectOrderProducts = (state: RootState) => state.order.products;

export const selectOrderStatus = (state: RootState) => state.order.status;

export default orderSlice.reducer;
