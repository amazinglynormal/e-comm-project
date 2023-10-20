import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import type { RootState } from "./store";

import createOrder from "./async-thunks/createOrder";
import deleteOrder from "./async-thunks/deleteOrder";
import updateOrder from "./async-thunks/updateOrder";
import Order from "../interfaces/order.interface";
import Product from "../types/Product.type";

interface OrderState {
  activeOrder: Order | undefined | null;
  completedOrder: Order | undefined | null;
  status: "idle" | "pending" | "succeeded" | "failed";
  error: string | null;
}

const initialState: OrderState = {
  activeOrder: undefined,
  completedOrder: undefined,
  status: "idle",
  error: null,
};

export const orderSlice = createSlice({
  name: "order",
  initialState,
  reducers: {
    setAsActiveOrder: (state, action: PayloadAction<Order>) => {
      state.activeOrder = action.payload;
    },
    addProductToOrder: (state, action: PayloadAction<Product>) => {
      if (state.activeOrder) {
        state.activeOrder.products.push(action.payload);
      }
    },
    removeProductFromOrder: (state, action: PayloadAction<Product>) => {
      if (state.activeOrder) {
        const index = state.activeOrder.products.findIndex(
          (p) => p.id === action.payload.id
        );
        if (index !== -1) {
          state.activeOrder?.products.splice(index, 1);
        }
      }
    },
    clearAllProductsFromOrder: (state) => {
      if (state.activeOrder) {
        state.activeOrder.products = [];
      }
    },
    resetActiveOrder: (state) => {
      state.activeOrder = null;
    },
    setCompletedOrder: (state, action: PayloadAction<Order>) => {
      state.completedOrder = action.payload;
    },
    updateOrderLocally: (state, action: PayloadAction<Order>) => {
      state.activeOrder = { ...action.payload };
    },
  },
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

export const {
  setAsActiveOrder,
  addProductToOrder,
  removeProductFromOrder,
  clearAllProductsFromOrder,
  resetActiveOrder,
  setCompletedOrder,
  updateOrderLocally,
} = orderSlice.actions;

export const selectOrder = (state: RootState) => state.order.activeOrder;

export const selectOrderProducts = (state: RootState) =>
  state.order.activeOrder?.products;

export const selectOrderStatus = (state: RootState) =>
  state.order.activeOrder?.status;

export const selectCompletedOrder = (state: RootState) =>
  state.order.completedOrder;

export default orderSlice.reducer;
