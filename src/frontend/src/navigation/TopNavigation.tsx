import { Link } from "react-router-dom";
import { useAppSelector } from "../hooks/redux-hooks";
import { selectUser } from "../state/userSlice";

import { CurrencySelector } from "./CurrencySelector";
import LoggedInUserButton from "./LoggedInUserButton";

export const TopNavigation = () => {
  const user = useAppSelector(selectUser);

  return (
    <div className="bg-gray-900">
      <div className="max-w-7xl mx-auto h-10 px-4 flex items-center justify-between sm:px-6 lg:px-8">
        <CurrencySelector version="desktop" />

        {user ? (
          <LoggedInUserButton user={user} />
        ) : (
          <div className="flex items-center space-x-6">
            <Link
              to="/login"
              className="text-sm font-medium text-white hover:text-gray-100"
            >
              Sign in
            </Link>
            <Link
              to="/signup"
              className="text-sm font-medium text-white hover:text-gray-100"
            >
              Create an account
            </Link>
          </div>
        )}
      </div>
    </div>
  );
};
