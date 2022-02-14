import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { RootState } from "../store";

interface ThunkArgs {
  currentPassword: string;
}

const userReauth = createAsyncThunk<number, ThunkArgs, { state: RootState }>(
  "user/userReauth",
  async ({ currentPassword }: ThunkArgs, { getState }) => {
    const userId = getState().user.user?.id;
    const token = sessionStorage.getItem("a_token");
    const userEmail = getState().user.user?.email;

    const response = await axios.post(
      `api/v1/auth/${userId}`,
      {
        email: userEmail,
        password: currentPassword,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    return response.status;
  }
);

export default userReauth;
