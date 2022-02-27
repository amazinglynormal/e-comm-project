import refresh from "./state/async-thunks/refresh";
import { Provider } from "react-redux";
import { store } from "./state/store";
import { StrictMode } from "react";
import ReactDOM from "react-dom";
import App from "./App";
import { checkLocalStorageForExistingOrder } from "./utils/localStorageOrderUtils";
import { setAsActiveOrder } from "./state/orderSlice";

async function attemptRefresh() {
  await store.dispatch(refresh());
}

attemptRefresh();

const existingOrder = checkLocalStorageForExistingOrder();

if (existingOrder) {
  store.dispatch(setAsActiveOrder(existingOrder));
}

ReactDOM.render(
  <StrictMode>
    <Provider store={store}>
      <App />
    </Provider>
  </StrictMode>,
  document.getElementById("root")
);
