import { FC, ReactElement } from "react";
import { render, RenderOptions } from "@testing-library/react";
import { Provider } from "react-redux";
import { store } from "../state/store";
import { BrowserRouter as Router } from "react-router-dom";
import { AlertProvider } from "../state/AlertContext";

const AllTheProviders: FC = ({ children }) => {
  return (
    <Provider store={store}>
      <AlertProvider>
        <Router>{children}</Router>
      </AlertProvider>
    </Provider>
  );
};

const customRender = (
  ui: ReactElement,
  options?: Omit<RenderOptions, "wrapper">
) => render(ui, { wrapper: AllTheProviders, ...options });

export * from "@testing-library/react";

export { customRender as render };
