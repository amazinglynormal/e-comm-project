import { unwrapResult } from "@reduxjs/toolkit";
import { ChangeEvent, FormEvent, useState } from "react";
import { useHistory } from "react-router-dom";
import { useAppDispatch } from "../hooks/redux-hooks";
import deleteUserAccount from "../state/async-thunks/deleteUserAccount";
import userReauth from "../state/async-thunks/userReauth";

const DeleteUserAccountForm = () => {
  const [showPasswordInput, setShowPasswordInput] = useState(false);
  const [password, setPassword] = useState("");
  const dispatch = useAppDispatch();
  const history = useHistory();

  const onClickDeleteAccount = () => {
    setShowPasswordInput(true);
  };

  const onClickCancel = () => {
    setShowPasswordInput(false);
  };

  const onSubmitHandler = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    try {
      const reauth = await dispatch(userReauth({ currentPassword: password }));
      const result = unwrapResult(reauth);
      if (result !== 200) return;

      const deleteAccount = await dispatch(deleteUserAccount());
      unwrapResult(deleteAccount);

      history.push("/");
    } catch (error) {
      console.error();
    }
  };

  const onChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  return (
    <section className="px-3 md:px-0">
      <div className="border-2 border-red-600 rounded-md shadow p-3 space-y-3 max-w-2xl mx-auto">
        <h2 className="text-2xl border-b-2 border-red-100">Delete Account</h2>
        <p>
          This action will permanently delete this account and all associated
          project Ideas.
        </p>
        <p>Reauthentication is required to perform this action.</p>
        <div className="flex items-center justify-center">
          {showPasswordInput ? (
            <form
              onSubmit={onSubmitHandler}
              className="flex flex-col space-y-3"
            >
              <input
                type="password"
                name="password"
                id="password"
                autoComplete="off"
                value={password}
                onChange={onChangeHandler}
                required
              />
              <div className="flex flex-col space-y-3 items-center md:flex-row md:space-y-0 md:justify-between">
                <button
                  type="button"
                  onClick={onClickCancel}
                  className="px-4 py-2 text-sm border border-indigo-100 text-indigo-700 bg-indigo-100 hover:bg-indigo-200 shadow-md  group inline-flex items-center font-medium rounded-md focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                  Cancel
                </button>

                <button
                  type="submit"
                  className="px-4 py-2 text-sm border border-red-100 text-white bg-red-700 hover:bg-red-800 shadow-md group inline-flex items-center font-medium rounded-md focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500"
                >
                  Yes, delete my account
                </button>
              </div>
            </form>
          ) : (
            <div>
              <button
                type="button"
                onClick={onClickDeleteAccount}
                className="px-4 py-2 text-sm border border-red-100 text-white bg-red-700 hover:bg-red-800 shadow-md group inline-flex items-center font-medium rounded-md focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500"
              >
                Delete Account
              </button>
            </div>
          )}
        </div>
      </div>
    </section>
  );
};

export default DeleteUserAccountForm;
