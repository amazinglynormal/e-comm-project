import { Fragment } from "react";
import { Popover, Transition } from "@headlessui/react";
import { UserIcon } from "@heroicons/react/solid";
import User from "../interfaces/user.interface";
import { Link } from "react-router-dom";

interface Props {
  user: User;
}

const LoggedInUserButton = ({ user }: Props) => {
  return (
    <div>
      <Popover className="relative">
        {() => (
          <>
            <Popover.Button className="text-white group bg-gray-900 rounded-md inline-flex items-center justify-center space-x-2 text-base font-medium focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 px-2 py-1 border border-white">
              <UserIcon
                className="text-white ml-2 h-5 w-5"
                aria-hidden="true"
              />
              <span>{user.username}</span>
            </Popover.Button>

            <Transition
              as={Fragment}
              enter="transition ease-out duration-200"
              enterFrom="opacity-0 translate-y-1"
              enterTo="opacity-100 translate-y-0"
              leave="transition ease-in duration-150"
              leaveFrom="opacity-100 translate-y-0"
              leaveTo="opacity-0 translate-y-1"
            >
              <Popover.Panel className="absolute -ml-4 mt-3 transform z-10 -translate-x-1/4 px-2 w-48 max-w-sm sm:px-0 lg:ml-0 lg:-translate-x-1/3">
                <div className="rounded-lg shadow-lg ring-1 ring-black ring-opacity-5 overflow-hidden">
                  <div className="bg-white px-5 py-2">
                    <Link
                      to="/profile"
                      className="p-3 flex items-start rounded-lg hover:bg-gray-50"
                    >
                      Profile
                    </Link>
                    <Link
                      to="/orderhistory"
                      className="p-3 flex items-start rounded-lg hover:bg-gray-50"
                    >
                      Order History
                    </Link>
                    <button className="w-full p-3 flex items-start rounded-lg hover:bg-gray-50">
                      Sign Out
                    </button>
                  </div>
                </div>
              </Popover.Panel>
            </Transition>
          </>
        )}
      </Popover>
    </div>
  );
};

export default LoggedInUserButton;
