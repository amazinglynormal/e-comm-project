import { ChangeEvent, FormEvent, useState } from "react";
import { Link } from "react-router-dom";
import { useAppDispatch } from "../hooks/redux-hooks";
import userLogin from "../state/async-thunks/userLogin";
import { useHistory } from "react-router";
import { unwrapResult } from "@reduxjs/toolkit";
import { setAsActiveOrder } from "../state/orderSlice";

const defaultFormData = { email: "", password: "" };

const LogIn = () => {
  const [formData, setFormData] = useState(defaultFormData);
  const dispatch = useAppDispatch();
  const history = useHistory();

  const onFormChange = (event: ChangeEvent<HTMLInputElement>) => {
    switch (event.target.id) {
      case "email":
        setFormData((data) => {
          return {
            ...data,
            email: event.target.value,
          };
        });
        break;
      case "password":
        setFormData((data) => {
          return {
            ...data,
            password: event.target.value,
          };
        });
        break;
      default:
        return;
    }
  };

  const onFormSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const { email, password } = formData;
    if (email.length > 0 && password.length >= 8) {
      try {
        const actionResult = await dispatch(userLogin({ email, password }));
        const userState = unwrapResult(actionResult);
        const activeOrder = userState.user.orders.find(
          (order) => order.status === "ACTIVE"
        );
        if (activeOrder) {
          dispatch(setAsActiveOrder(activeOrder));
        }
        setFormData(defaultFormData);
        history.push("/");
      } catch (error) {
        console.log(error);
      }
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col justify-center py-12 sm:px-6 lg:px-8">
      <div className="sm:mx-auto sm:w-full sm:max-w-md">
        <img
          className="mx-auto h-12 w-auto"
          src="https://tailwindui.com/img/logos/workflow-mark-indigo-600.svg"
          alt="Workflow"
        />
        <h2 className="mt-6 text-center text-3xl font-extrabold text-gray-900">
          Sign in to your account
        </h2>
      </div>

      <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
        <div className="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
          <form className="space-y-6" onSubmit={onFormSubmit}>
            <div>
              <label
                htmlFor="email"
                className="block text-sm font-medium text-gray-700"
              >
                Email address
              </label>
              <div className="mt-1">
                <input
                  id="email"
                  name="email"
                  type="email"
                  value={formData.email}
                  onChange={onFormChange}
                  autoComplete="email"
                  required
                  className="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                />
              </div>
            </div>

            <div>
              <label
                htmlFor="password"
                className="block text-sm font-medium text-gray-700"
              >
                Password
              </label>
              <div className="mt-1">
                <input
                  id="password"
                  name="password"
                  type="password"
                  value={formData.password}
                  onChange={onFormChange}
                  autoComplete="current-password"
                  required
                  className="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                />
              </div>
            </div>

            <div className="flex items-center justify-between">
              <div className="flex items-center">
                <input
                  id="remember-me"
                  name="remember-me"
                  type="checkbox"
                  className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                />
                <label
                  htmlFor="remember-me"
                  className="ml-2 block text-sm text-gray-900"
                >
                  Remember me
                </label>
              </div>

              <div className="text-sm">
                <Link
                  to="/passwordrecovery"
                  className="font-medium text-indigo-600 hover:text-indigo-500"
                >
                  Forgot your password?
                </Link>
              </div>
            </div>

            <div>
              <button
                type="submit"
                className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
              >
                Sign in
              </button>
            </div>
          </form>
        </div>
      </div>
      <p className="self-center mt-5">
        Don&apos;t have an account?{" "}
        <Link to="/signup" className="text-indigo-600 hover:text-indigo-700">
          Sign up here.
        </Link>
      </p>
    </div>
  );
};

export default LogIn;
