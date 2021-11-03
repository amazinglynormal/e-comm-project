import { BrowserRouter as Router, Route, Switch } from "react-router-dom";

import { StoreNavigation } from "./navigation/StoreNavigation";
import { OrderSummary } from "./order/OrderSummary";
import { Footer } from "./components/Footer";

import ProductDetails from "./product/ProductDetails";
import OrderHistory from "./order/OrderHistory";
import Products from "./product/Products";
import Checkout from "./order/Checkout";
import Profile from "./user/Profile";
import SignUp from "./user/SignUp";
import LogIn from "./user/LogIn";
import Cart from "./order/Cart";
import Home from "./home/Home";

import { CurrencyProvider } from "./state/CurrencyContext";

import "tailwindcss/tailwind.css";

const App = () => {
  return (
    <Router>
      <CurrencyProvider>
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
          <Route path="/ordersummary">
            <OrderSummary />
          </Route>
          <Route path="/orderhistory">
            <OrderHistory />
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
      </CurrencyProvider>
    </Router>
  );
};

export default App;
