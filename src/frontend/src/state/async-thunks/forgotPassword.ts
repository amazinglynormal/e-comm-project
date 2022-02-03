import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";

const forgotPassword = createAsyncThunk(
  "user/forgotPassword",
  async (email: string) => {
    const response = await axios.post("/api/v1/auth/forgotpassword", { email });
    return response.status;
  }
);

export default forgotPassword;
