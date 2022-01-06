import { useState } from "react";
import classNames from "../utils/classNames";
import UpdateInfoForm from "./UpdateInfoForm";

interface Props {
  id: string;
  info?: string;
}

const ProfileInformationItem = ({ id, info }: Props) => {
  const currentInfo = info ? info : " ";

  const [showUpdateForm, setShowUpdateForm] = useState(false);

  return (
    <div
      className={classNames(
        showUpdateForm ? "" : "sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4",
        "py-4"
      )}
    >
      {showUpdateForm ? (
        <UpdateInfoForm
          id={id}
          reauthRequired={id === "Password"}
          setShowUpdateForm={setShowUpdateForm}
          currentInfo={id !== "Password" ? currentInfo : ""}
        />
      ) : (
        <>
          <dt className="text-sm font-medium text-gray-500">{id}</dt>
          <dd className="mt-1 flex text-sm text-gray-900 sm:mt-0 sm:col-span-2">
            <span className="flex-grow">
              {id === "Password" ? "••••••••" : currentInfo}
            </span>
            <span className="ml-4 flex-shrink-0">
              <button
                type="button"
                className="bg-white rounded-md font-medium text-purple-600 hover:text-purple-500 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500"
                onClick={() => setShowUpdateForm(true)}
              >
                Update
              </button>
            </span>
          </dd>
        </>
      )}
    </div>
  );
};

export default ProfileInformationItem;
