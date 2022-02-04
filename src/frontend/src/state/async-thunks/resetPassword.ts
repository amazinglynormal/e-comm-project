import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";

interface Args {
  newPassword: string;
  resetToken: string;
}

const resetPassword = createAsyncThunk(
  "user/resetPassword",
  async ({ newPassword, resetToken }: Args) => {
    const response = await axios.patch(
      `http://localhost:3000/api/v1/auth/reset`,
      {
        newPassword,
        resetToken,
      }
    );

    return response.data;
  }
);

export default resetPassword;
