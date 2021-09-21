import { BrowserRouter as Router, Route, Switch } from "react-router-dom";

import { StoreNavigation } from "./navigation/StoreNavigation";
import { Footer } from "./components/Footer";

import ProductDetails from "./product/ProductDetails";
import Products from "./product/Products";
import Checkout from "./order/Checkout";
import Profile from "./user/Profile";
import SignUp from "./user/SignUp";
import LogIn from "./user/LogIn";
import Home from "./home/Home";
import Cart from "./order/Cart";

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
        <Route path="/cart">
          <Cart />
        </Route>
        <Route path="/profile">
          <Profile />
        </Route>
        <Route path="/checkout">
          <Checkout />
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
