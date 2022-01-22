import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import jwtDecode from "jwt-decode";
import DecodedToken from "../../interfaces/decodedToken.interface";
import User from "../../interfaces/user.interface";
import extractJwtTokenFromResponseHeaders from "../../utils/extractJwtTokenFromResponse";

const refresh = createAsyncThunk("user/refresh", async () => {
  const refreshToken = localStorage.getItem("r_token");
  const response = await axios.post(
    "/api/v1/auth/refresh",
    {},
    {
      headers: {
        Authorization: `Bearer ${refreshToken}`,
      },
    }
  );

  const accessToken = extractJwtTokenFromResponseHeaders(response.headers);

  sessionStorage.setItem("a_token", accessToken);

  const decodedToken: DecodedToken = jwtDecode(accessToken);

  const getUserInfoResponse = await axios.get<User>(
    `/api/v1/users/${decodedToken.sub}`,
    {
      headers: { Authorization: `Bearer ${accessToken}` },
    }
  );

  return { user: getUserInfoResponse.data };
});

export default refresh;
