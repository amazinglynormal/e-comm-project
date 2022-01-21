import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import Order from "../../interfaces/order.interface";
import { RootState } from "../store";

interface ThunkArgs {
  status?: string;
  addProduct?: number;
  removeProduct?: number;
}

const updateOrder = createAsyncThunk<Order, ThunkArgs, { state: RootState }>(
  "order/updateOrder",
  async (updateOrder: ThunkArgs, { getState }) => {
    const userId = getState().user.user?.id;
    const token = sessionStorage.getItem("token");
    const orderId = getState().order.activeOrder?.id;

    const response = await axios.put<Order>(
      `/api/v1/users/${userId}/orders/${orderId}`,
      {
        ...updateOrder,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    return response.data;
  }
);

export default updateOrder;
