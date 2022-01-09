import {
  CheckCircleIcon,
  XCircleIcon,
  ExclamationIcon,
  XIcon,
} from "@heroicons/react/solid";
import { useAlert } from "../state/AlertContext";
import classNames from "../utils/classNames";

const alertIcon: { [alertType: string]: JSX.Element } = {
  success: (
    <CheckCircleIcon className="h-5 w-5 text-green-400" aria-hidden="true" />
  ),
  warning: (
    <ExclamationIcon className="h-5 w-5 text-yellow-400" aria-hidden="true" />
  ),
  error: <XCircleIcon className="h-5 w-5 text-red-400" aria-hidden="true" />,
};

const alertColor: { [alertType: string]: string } = {
  success: "green",
  warning: "yellow",
  error: "red",
};

const AlertWithDismissButton = () => {
  const { showAlert, alertType, message, dismissAlert } = useAlert();
  return (
    <div
      className={classNames(
        showAlert ? "absolute inset-x-0 bottom-0" : "hidden",
        `rounded-md bg-${alertColor[alertType]}-50 p-4`
      )}
    >
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
              onClick={() => dismissAlert()}
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
