import { unwrapResult } from "@reduxjs/toolkit";
import { ChangeEvent, FormEvent, useState } from "react";
import { Link, useHistory } from "react-router-dom";
import { useAppDispatch } from "../hooks/redux-hooks";
import userSignUp from "../state/async-thunks/userSignUp";

const defaultFormData = {
  name: "",
  email: "",
  password: "",
};

const SignUp = () => {
  const [formData, setFormData] = useState(defaultFormData);
  const dispatch = useAppDispatch();
  const history = useHistory();

  const onFormChange = (event: ChangeEvent<HTMLInputElement>) => {
    switch (event.target.id) {
      case "name":
        setFormData((data) => {
          return {
            ...data,
            name: event.target.value,
          };
        });
        break;
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
    const { name, email, password } = formData;
    if (name.length > 0 && email.length > 0 && password.length >= 8) {
      try {
        console.log(password);
        const actionResult = await dispatch(
          userSignUp({ username: name, email, password })
        );
        unwrapResult(actionResult);
        setFormData(defaultFormData);
        history.push("");
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
          Create your account
        </h2>
      </div>

      <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
        <div className="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
          <form className="space-y-6" onSubmit={onFormSubmit}>
            <div>
              <label
                htmlFor="name"
                className="block text-sm font-medium text-gray-700"
              >
                Name
              </label>
              <div className="mt-1">
                <input
                  id="name"
                  name="name"
                  type="text"
                  value={formData.name}
                  onChange={onFormChange}
                  required
                  className="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                />
              </div>
            </div>
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
                  minLength={8}
                  className="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                />
              </div>
            </div>

            <div>
              <button
                type="submit"
                className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
              >
                Create account
              </button>
            </div>
          </form>
        </div>
      </div>
      <p className="self-center mt-5">
        Already have an account?{" "}
        <Link to="/login" className="text-indigo-600 hover:text-indigo-700">
          Log in here.
        </Link>
      </p>
    </div>
  );
};

export default SignUp;
