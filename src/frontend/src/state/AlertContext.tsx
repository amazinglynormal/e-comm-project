import { createContext, useContext, useState } from "react";

interface ProviderProps {
  children: JSX.Element | JSX.Element[];
}

export type AlertType = "success" | "warning" | "error";

const AlertContext = createContext({
  showAlert: false,
  handleAlert: (bool: boolean) => console.log(bool),
  type: "success",
  setType: (type: AlertType) => console.log(type),
  message: "",
  setMessage: (message: string) => console.log(message),
});

const useAlert = () => {
  const { showAlert, type, message, handleAlert, setType, setMessage } =
    useContext(AlertContext);

  const triggerAlert = (alertType: AlertType, alertMessage: string) => {
    setType(alertType);
    setMessage(alertMessage);
    handleAlert(true);
  };

  const dismissAlert = () => handleAlert(false);

  return { showAlert, alertType: type, message, triggerAlert, dismissAlert };
};

const AlertProvider = ({ children }: ProviderProps) => {
  const [showAlert, setShowAlert] = useState(false);
  const [alertType, setAlertType] = useState<AlertType>("success");
  const [alertMessage, setAlertMessage] = useState("");

  return (
    <AlertContext.Provider
      value={{
        showAlert,
        handleAlert: setShowAlert,
        type: alertType,
        setType: setAlertType,
        message: alertMessage,
        setMessage: setAlertMessage,
      }}
    >
      {children}
    </AlertContext.Provider>
  );
};

export { AlertProvider, useAlert };
