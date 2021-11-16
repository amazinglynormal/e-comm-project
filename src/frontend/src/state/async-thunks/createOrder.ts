import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import Product from "../../types/Product.type";
import { RootState } from "../store";
import Order from "../../interfaces/order.interface";

const createOrder = createAsyncThunk<Order, Product, { state: RootState }>(
  "order/createOrder",
  async (product: Product, { getState }) => {
    const userId = getState().user.id;
    const token = getState().user.token;
    const response = await axios.post<Order>(
      `/api/v1/users/${userId}/orders`,
      {
        userId,
        productIds: [product.id],
        orderStatus: "ACTIVE",
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
