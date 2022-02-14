import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { RootState } from "../store";
import Order from "../../interfaces/order.interface";

const createOrder = createAsyncThunk<Order, number, { state: RootState }>(
  "order/createOrder",
  async (productId: number, { getState }) => {
    const userId = getState().user.user?.id;
    const token = sessionStorage.getItem("a_token");
    const response = await axios.post<Order>(
      `/api/v1/users/${userId}/orders`,
      {
        productIds: [productId],
        status: "ACTIVE",
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

export default createOrder;
