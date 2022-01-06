import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import User from "../../interfaces/user.interface";
import { RootState } from "../store";

interface ThunkArgs {
  username?: string;
  email?: string;
  line1?: string;
  line2?: string;
  line3?: string;
  city?: string;
  province?: string;
  country?: string;
  zipCode?: string;
}

const updateUserInfo = createAsyncThunk<User, ThunkArgs, { state: RootState }>(
  "user/updateUserInfo",
  async (updateUserDTO: ThunkArgs, { getState }) => {
    const token = getState().user.token;
    const userId = getState().user.user?.id;
    const response = await axios.put<User>(
      `api/v1/users/${userId}`,
      {
        ...updateUserDTO,
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

export default updateUserInfo;
