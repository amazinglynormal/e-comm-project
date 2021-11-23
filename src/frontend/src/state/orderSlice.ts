import { createSlice } from "@reduxjs/toolkit";
import type { RootState } from "./store";

import createOrder from "./async-thunks/createOrder";
import deleteOrder from "./async-thunks/deleteOrder";
import updateOrder from "./async-thunks/updateOrder";
import Order from "../interfaces/order.interface";

interface OrderState {
  activeOrder: Order | undefined;
  status: "idle" | "pending" | "succeeded" | "failed";
  error: string | null;
}

const initialState: OrderState = {
  activeOrder: undefined,
  status: "idle",
  error: null,
};

export const orderSlice = createSlice({
  name: "order",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(createOrder.pending, (state) => {
      state.status = "pending";
    });

    builder.addCase(createOrder.fulfilled, (state, action) => {
      state.status = "succeeded";
      state.activeOrder = { ...action.payload };
      state.status = "idle";
    });

    builder.addCase(createOrder.rejected, (state, action) => {
      state.status = "failed";
      if (action.error.message) {
        state.error = action.error.message;
      }
    });

    builder.addCase(deleteOrder.rejected, (state, action) => {
      state.status = "failed";
      if (action.error.message) {
        state.error = action.error.message;
      }
    });

    builder.addCase(deleteOrder.fulfilled, (state) => {
      state.status = "succeeded";
      state.activeOrder = initialState.activeOrder;
      state.status = "idle";
    });

    builder.addCase(updateOrder.pending, (state) => {
      state.status = "pending";
    });

    builder.addCase(updateOrder.fulfilled, (state, action) => {
      state.status = "succeeded";
      state.activeOrder = { ...action.payload };
      state.status = "idle";
    });

    builder.addCase(updateOrder.rejected, (state, action) => {
      state.status = "failed";
      if (action.error.message) {
        state.error = action.error.message;
      }
    });
  },
});

export const selectOrder = (state: RootState) => state.order.activeOrder;

export const selectOrderProducts = (state: RootState) =>
  state.order.activeOrder?.products;

export const selectOrderStatus = (state: RootState) =>
  state.order.activeOrder?.orderStatus;

export default orderSlice.reducer;
