import { unwrapResult } from "@reduxjs/toolkit";
import {
  ChangeEvent,
  useState,
  Dispatch,
  SetStateAction,
  FormEvent,
} from "react";
import { useAppDispatch } from "../hooks/redux-hooks";
import { useAlert } from "../state/AlertContext";
import updateUserInfo from "../state/async-thunks/updateUserInfo";
import userReauth from "../state/async-thunks/userReauth";
import classNames from "../utils/classNames";

interface Props {
  id: string;
  reauthRequired?: boolean;
  setShowUpdateForm: Dispatch<SetStateAction<boolean>>;
  currentInfo?: string;
}

const inputTypes = { Password: "password", Email: "email" };

const UpdateInfoForm = ({
  id,
  reauthRequired,
  setShowUpdateForm,
  currentInfo,
}: Props) => {
  const [currentPassword, setCurrentPassword] = useState("");
  const [updatedDetail, setUpdatedDetail] = useState(currentInfo || "");
  const dispatch = useAppDispatch();
  const { triggerAlert } = useAlert();

  const onChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
    if (event.target.id === "current-password") {
      setCurrentPassword(event.target.value);
    } else if (event.target.id === id) {
      setUpdatedDetail(event.target.value);
    }
  };

  const onSubmitHandler = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (reauthRequired && !currentPassword) return;

    if (reauthRequired) {
      try {
        const reauth = await dispatch(userReauth({ currentPassword }));
        unwrapResult(reauth);
      } catch (error) {
        triggerAlert("error", "Could not update. Please try again");
        return;
      }
    }

    try {
      const detailToUpdate = id === "Name" ? "username" : id.toLowerCase();
      const update = await dispatch(
        updateUserInfo({ [detailToUpdate]: updatedDetail })
      );
      unwrapResult(update);
      setShowUpdateForm(false);
      triggerAlert("success", "Successfully Updated");
    } catch (error) {
      triggerAlert("error", "Could not update. Please try again");
    }
  };

  return (
    <form
      onSubmit={onSubmitHandler}
      className="flex items-center justify-between"
    >
      <div
        className={classNames(
          id === "Password"
            ? "space-y-3 mx-auto md:text-lg xl:text-xl"
            : "flex w-3/4 items-center space-x-6",
          "pr-2 w-3/4"
        )}
      >
        <label
          htmlFor={id}
          className="block sm:text-sm md:text-md font-medium text-gray-700"
        >
          {id === "Password" ? "New Password" : id}
        </label>
        <input
          id={id}
          type={id === "Password" || id === "Email" ? inputTypes[id] : "text"}
          required
          value={updatedDetail}
          onChange={onChangeHandler}
          autoComplete={id === "Password" ? "new-password" : ""}
          className="block w-full py-2 sm:text-sm md:text-md border rounded-md shadow-sm placeholder-gray-400 focus:outline-none px-3 border-gray-300 focus:ring-indigo-500 focus:border-indigo-500"
        />
        {reauthRequired && (
          <>
            <label
              htmlFor="current-password"
              className="block sm:text-sm md:text-md font-medium text-gray-700"
            >
              Current Password
            </label>
            <input
              id="current-password"
              type="password"
              required
              value={currentPassword}
              onChange={onChangeHandler}
              autoComplete="current-password"
              className="block w-full py-2 sm:text-sm md:text-md border rounded-md shadow-sm placeholder-gray-400 focus:outline-none px-3 border-gray-300 focus:ring-indigo-500 focus:border-indigo-500"
            />
          </>
        )}
      </div>
      <div className="flex items-center space-x-3 md:space-x-5">
        <button
          type="button"
          onClick={() => setShowUpdateForm(false)}
          className="bg-gray-300 rounded-md px-5 py-2"
        >
          Cancel
        </button>
        <button
          type="submit"
          className="px-5 py-2 bg-green-300 rounded-md  md:px-5"
        >
          Update
        </button>
      </div>
    </form>
  );
};

export default UpdateInfoForm;
