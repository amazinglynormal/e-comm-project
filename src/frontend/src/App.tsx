import React, { Suspense } from "react";
import { HashRouter as Router, Route, Switch } from "react-router-dom";

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
import { AlertProvider } from "./state/AlertContext";

import "tailwindcss/tailwind.css";
import AlertWithDismissButton from "./components/AlertWithDismissButton";

const App = () => {
  return (
    <Router>
      <AlertProvider>
        <CurrencyProvider>
          <StoreNavigation />
          <Suspense fallback={<div>loading...</div>}>
            <Switch>
              <Route path="/product/details/:id">
                <ProductDetails />
              </Route>
              <Route
                path={[
                  "/products/:category/:subCategory",
                  "/products/:category/",
                ]}
              >
                <Products />
              </Route>
              <Route path="/cart/:orderId">
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
        <AlertWithDismissButton />
      </AlertProvider>
    </Router>
  );
};

export default App;
