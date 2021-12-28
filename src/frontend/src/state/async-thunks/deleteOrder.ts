import { createAsyncThunk } from "@reduxjs/toolkit";
import { RootState } from "../store";
import axios from "axios";

const deleteOrder = createAsyncThunk<void, void, { state: RootState }>(
  "order/deleteOrder",
  async (_, { getState }) => {
    const userId = getState().user.user?.id;
    const token = getState().user.token;
    const orderId = getState().order.activeOrder?.id;

    const response = await axios.delete<void>(
      `/api/v1/users/${userId}/orders/${orderId}`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    return response.data;
  }
);

export default deleteOrder;
