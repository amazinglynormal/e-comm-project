import { TextInput } from "../components/TextInput";
import { Button } from "../components/Button";
import { ChangeEvent, FormEvent, useState } from "react";
import { useHistory, useParams } from "react-router";
import { useAppDispatch } from "../hooks/redux-hooks";
import resetPassword from "../state/async-thunks/resetPassword";
import { unwrapResult } from "@reduxjs/toolkit";
import { Link } from "react-router-dom";

interface Params {
  resetToken: string;
}

const ResetPassword = () => {
  const { resetToken } = useParams<Params>();
  const history = useHistory();
  const dispatch = useAppDispatch();
  const [formData, setFormData] = useState({
    newPassword: "",
    confirmNewPassword: "",
  });
  const [showTryAgain, setShowTryAgain] = useState(false);
  const [error, setError] = useState({
    active: false,
    message: "",
  });

  const onChange = (event: ChangeEvent<HTMLInputElement>) => {
    setFormData((prevState) => {
      return { ...prevState, [event.target.name]: event.target.value };
    });
  };

  const onSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (formData.newPassword !== formData.confirmNewPassword) {
      setError({ active: true, message: "Both input fields must match." });
    }

    try {
      const result = await dispatch(
        resetPassword({ newPassword: formData.newPassword, resetToken })
      );
      unwrapResult(result);
      history.push("/login");
    } catch (error) {
      setShowTryAgain(true);
    }
  };

  return (
    <div>
      {showTryAgain ? (
        <div>
          <h1>Something went wrong!</h1>
          <p>
            Unfortunately, in the interest of security, this means you will have
            to start the reset password process again. We apologize for the
            inconvience.
          </p>
          <Link to="/forgotpassword">Request reset password here.</Link>
        </div>
      ) : (
        <form onSubmit={onSubmit}>
          <div>
            <label htmlFor="newPassword">New Password</label>
            <TextInput
              type="password"
              name="newPassword"
              id="newPassword"
              value={formData.newPassword}
              required
              onChangeHandler={onChange}
              autoComplete="new-password"
              activeError={error.active}
              errorMessage={error.message}
            />
          </div>
          <div>
            <label htmlFor="confirmNewPassword">Confirm new password</label>
            <TextInput
              type="password"
              name="confirmNewPassword"
              id="confirmNewPassword"
              value={formData.confirmNewPassword}
              required
              onChangeHandler={onChange}
              autoComplete="new-password"
              activeError={error.active}
              errorMessage={error.message}
            />
          </div>
          <div>
            <Button
              type="submit"
              variant="primary"
              size="large"
              text="Change password"
            />
          </div>
        </form>
      )}
    </div>
  );
};

export default ResetPassword;
