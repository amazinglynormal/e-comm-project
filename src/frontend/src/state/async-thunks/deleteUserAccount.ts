import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { RootState } from "../store";

const deleteUserAccount = createAsyncThunk<number, void, { state: RootState }>(
  "user/deleteAccount",
  async (_, { getState }) => {
    const token = sessionStorage.getItem("token");
    const userId = getState().user.user?.id;

    const response = await axios.delete(`api/v1/users/${userId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return response.status;
  }
);

export default deleteUserAccount;
