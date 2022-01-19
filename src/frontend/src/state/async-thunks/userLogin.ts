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

const userLogin = createAsyncThunk(
  "user/userLogin",
  async ({ email, password }: ThunkArgs) => {
    const loginResponse = await axios.post("/api/v1/auth/login", {
      email,
      password,
    });

    const token = extractJwtTokenFromResponseHeaders(loginResponse.headers);

    const decodedToken: DecodedToken = jwtDecode(token);

    const getUserInfoResponse = await axios.get<User>(
      `/api/v1/users/${decodedToken.sub}`,
      {
        headers: { Authorization: `Bearer ${token}` },
      }
    );

    return { user: getUserInfoResponse.data, token };
  }
);

export default userLogin;
