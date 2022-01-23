import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";

const userLogout = createAsyncThunk("user/logout", async () => {
  const accessToken = sessionStorage.getItem("a_token");

  const response = await axios.post(
    "/api/v1/auth/logout",
    {},
    {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    }
  );

  return response.status;
});

export default userLogout;
