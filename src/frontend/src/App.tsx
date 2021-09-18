import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import { StoreNavigation } from "./components/StoreNavigation";

import Home from "./home/Home";

import "tailwindcss/tailwind.css";

const App = () => {
  return (
    <Router>
      <StoreNavigation />
      <Switch>
        <Route exact path="/">
          <Home />
        </Route>
      </Switch>
    </Router>
  );
};

export default App;
