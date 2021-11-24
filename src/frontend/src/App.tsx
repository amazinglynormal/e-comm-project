import React, { Suspense } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";

import StoreNavigation from "./navigation/StoreNavigation";
import OrderSummary from "./order/OrderSummary";
import OrderHistory from "./order/OrderHistory";
import Footer from "./components/Footer";
import Checkout from "./order/Checkout";
import Cart from "./order/Cart";

const ProductDetails = React.lazy(() => import("./product/ProductDetails"));
const Products = React.lazy(() => import("./product/Products"));
const Profile = React.lazy(() => import("./user/Profile"));
const SignUp = React.lazy(() => import("./user/SignUp"));
const LogIn = React.lazy(() => import("./user/LogIn"));
const Home = React.lazy(() => import("./home/Home"));

import { CurrencyProvider } from "./state/CurrencyContext";

import "tailwindcss/tailwind.css";

const App = () => {
  return (
    <Router>
      <CurrencyProvider>
        <StoreNavigation />
        <Suspense fallback={<div>loading...</div>}>
          <Switch>
            <Route path="/products/:id">
              <ProductDetails />
            </Route>
            <Route exact path="/products">
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
        </Suspense>
        <Footer />
      </CurrencyProvider>
    </Router>
  );
};

export default App;
