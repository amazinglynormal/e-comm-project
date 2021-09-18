import { BrowserRouter as Router, Route, Switch } from "react-router-dom";

import { StoreNavigation } from "./components/StoreNavigation";
import { Footer } from "./components/Footer";

import Home from "./home/Home";
import SignUp from "./signup/SignUp";

import "tailwindcss/tailwind.css";

const App = () => {
  return (
    <Router>
      <StoreNavigation />
      <Switch>
        <Route path="/signup">
          <SignUp />
        </Route>
        <Route exact path="/">
          <Home />
        </Route>
      </Switch>
      <Footer />
    </Router>
  );
};

export default App;
