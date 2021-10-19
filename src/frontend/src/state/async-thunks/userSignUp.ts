import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import User from "../../interfaces/user.interface";

interface ThunkArgs {
  username: string;
  email: string;
  password: string;
}

const userSignUp = createAsyncThunk(
  "user/userSignUp",
  async ({ username, email, password }: ThunkArgs) => {
    const signUpResponse = await axios.post<User>("/api/v1/users", {
      username,
      email,
      password,
    });
    return signUpResponse.data;
  }
);

export default userSignUp;
