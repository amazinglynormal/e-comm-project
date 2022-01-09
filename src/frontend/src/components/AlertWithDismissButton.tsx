import { Dispatch, SetStateAction } from "react";
import {
  CheckCircleIcon,
  XCircleIcon,
  ExclamationIcon,
  XIcon,
} from "@heroicons/react/solid";

interface Props {
  message: string;
  alertType: "success" | "warning" | "error";
  setShowAlert: Dispatch<SetStateAction<boolean>>;
}

const alertIcon = {
  success: (
    <CheckCircleIcon className="h-5 w-5 text-green-400" aria-hidden="true" />
  ),
  warning: (
    <ExclamationIcon className="h-5 w-5 text-yellow-400" aria-hidden="true" />
  ),
  error: <XCircleIcon className="h-5 w-5 text-red-400" aria-hidden="true" />,
};

const alertColor = {
  success: "green",
  warning: "yellow",
  error: "red",
};

const AlertWithDismissButton = ({
  message,
  alertType,
  setShowAlert,
}: Props) => {
  return (
    <div className={`rounded-md bg-${alertColor[alertType]}-50 p-4`}>
      <div className="flex">
        <div className="flex-shrink-0">{alertIcon[alertType]}</div>
        <div className="ml-3">
          <p
            className={`text-sm font-medium text-${alertColor[alertType]}-800`}
          >
            {message}
          </p>
        </div>
        <div className="ml-auto pl-3">
          <div className="-mx-1.5 -my-1.5">
            <button
              type="button"
              className={`inline-flex bg-${alertColor[alertType]}-50 rounded-md p-1.5 text-${alertColor[alertType]}-500 hover:bg-${alertColor[alertType]}-100 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-${alertColor[alertType]}-50 focus:ring-${alertColor[alertType]}-600`}
              onClick={() => setShowAlert(false)}
            >
              <span className="sr-only">Dismiss</span>
              <XIcon className="h-5 w-5" aria-hidden="true" />
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AlertWithDismissButton;
