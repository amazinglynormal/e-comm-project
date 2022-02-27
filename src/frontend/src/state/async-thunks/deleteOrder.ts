import { createAsyncThunk } from "@reduxjs/toolkit";
import { RootState } from "../store";
import axios from "axios";

const deleteOrder = createAsyncThunk<void, number, { state: RootState }>(
  "order/deleteOrder",
  async (orderId: number, { getState }) => {
    const userId = getState().user.user?.id;
    const token = sessionStorage.getItem("a_token");

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
