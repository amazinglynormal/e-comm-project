import { BrowserRouter as Router, Route, Switch } from "react-router-dom";

import Home from "./home/Home";

import "tailwindcss/tailwind.css";

const App = () => {
  return (
    <Router>
      <h1 className="text-2xl">Hello World</h1>
      <Switch>
        <Route exact path="/">
          <Home />
        </Route>
      </Switch>
    </Router>
  );
};

export default App;
