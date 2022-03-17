import { __ } from "@headlessui/react/dist/types";
import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import Order from "../../interfaces/order.interface";
import { RootState } from "../store";

const fetchCompletedUserOrders = createAsyncThunk<
  Order[],
  void,
  { state: RootState }
>("user/getCompletedOrders", async (_, { getState }) => {
  const userId = getState().user.user?.id;
  const token = sessionStorage.getItem("a_token");
  const response = await axios.get<Order[]>(`/api/v1/users/${userId}/orders`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  return response.data;
});

export default fetchCompletedUserOrders;
