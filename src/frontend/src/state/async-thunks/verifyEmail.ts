import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";

const verifyEmail = createAsyncThunk(
  "user/verifyEmail",
  async (verificationHash: string) => {
    const response = await axios.patch(
      `/api/v1/auth/verify/${verificationHash}`
    );

    return response.status;
  }
);

export default verifyEmail;
