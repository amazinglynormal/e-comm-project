import { BrowserRouter as Router, Route, Switch } from "react-router-dom";

import { StoreNavigation } from "./navigation/StoreNavigation";
import { Footer } from "./components/Footer";

import Home from "./home/Home";
import SignUp from "./signup/SignUp";
import LogIn from "./login/LogIn";
import ProductDetails from "./product/ProductDetails";
import Products from "./product/Products";

import "tailwindcss/tailwind.css";

const App = () => {
  return (
    <Router>
      <StoreNavigation />
      <Switch>
        <Route path="/products/:id">
          <ProductDetails />
        </Route>
        <Route path="/products">
          <Products />
        </Route>
        <Route path="/login">
          <LogIn />
        </Route>
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
