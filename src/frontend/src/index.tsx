import { StrictMode } from "react";
import ReactDOM from "react-dom";
import { Provider } from "react-redux";
import { store } from "./state/store";
import App from "./App";
import refresh from "./state/async-thunks/refresh";

async function attemptRefresh() {
  await store.dispatch(refresh());
}

attemptRefresh();

ReactDOM.render(
  <StrictMode>
    <Provider store={store}>
      <App />
    </Provider>
  </StrictMode>,
  document.getElementById("root")
);
