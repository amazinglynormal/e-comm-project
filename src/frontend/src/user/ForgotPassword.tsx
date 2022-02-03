import { ChangeEvent, FormEvent, useState } from "react";
import { Link } from "react-router-dom";
import { useAppDispatch } from "../hooks/redux-hooks";
import forgotPassword from "../state/async-thunks/forgotPassword";

const ForgotPassword = () => {
  const [email, setEmail] = useState("");
  const [submitted, setSubmitted] = useState(false);
  const dispatch = useAppDispatch();

  const onEmailChange = (event: ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };

  const onSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (email === "") return;

    dispatch(forgotPassword(email));
    setEmail("");
    setSubmitted(true);
  };

  return (
    <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
      <form onSubmit={onSubmit}>
        <div className="mb-4">
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
              value={email}
              onChange={onEmailChange}
              autoComplete="email"
              required
              className="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            />
          </div>
        </div>
        <button
          type="submit"
          className="px-4 py-2 text-base border border-transparent text-white bg-indigo-600 hover:bg-indigo-700 shadow-md group inline-flex items-center font-medium rounded-md focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
        >
          Request password reset
        </button>
      </form>
      <div className="my-4">
        <Link to="/" className="hover:text-indigo-600">
          Return to home page --&gt;
        </Link>
      </div>
      {submitted && (
        <p>
          If the email provided exists in our database, instructions to reset
          your password will arrive shortly.
        </p>
      )}
    </div>
  );
};

export default ForgotPassword;
