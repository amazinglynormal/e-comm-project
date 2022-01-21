import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import jwtDecode from "jwt-decode";
import DecodedToken from "../../interfaces/decodedToken.interface";
import User from "../../interfaces/user.interface";
import extractJwtTokenFromResponseHeaders from "../../utils/extractJwtTokenFromResponse";

interface ThunkArgs {
  email: string;
  password: string;
}

interface LoginResponse {
  refreshToken: string;
}

const userLogin = createAsyncThunk(
  "user/userLogin",
  async ({ email, password }: ThunkArgs) => {
    const loginResponse = await axios.post<LoginResponse>(
      "/api/v1/auth/login",
      {
        email,
        password,
      }
    );

    const accessToken = extractJwtTokenFromResponseHeaders(
      loginResponse.headers
    );

    sessionStorage.setItem("token", accessToken);
    localStorage.setItem("token", loginResponse.data.refreshToken);

    const decodedToken: DecodedToken = jwtDecode(accessToken);

    const getUserInfoResponse = await axios.get<User>(
      `/api/v1/users/${decodedToken.sub}`,
      {
        headers: { Authorization: `Bearer ${accessToken}` },
      }
    );

    return { user: getUserInfoResponse.data };
  }
);

export default userLogin;
