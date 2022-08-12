import React, { Suspense } from "react";
import { HashRouter as Router, Route, Switch } from "react-router-dom";

import AlertWithDismissButton from "./components/AlertWithDismissButton";
import StoreNavigation from "./navigation/StoreNavigation";
import OrderSummary from "./order/OrderSummary";
import OrderHistory from "./order/OrderHistory";
import Footer from "./components/Footer";
import Cart from "./order/Cart";

const ProductDetails = React.lazy(() => import("./product/ProductDetails"));
const ForgotPassword = React.lazy(() => import("./user/ForgotPassword"));
const EmailVerified = React.lazy(() => import("./user/EmailVerified"));
const ResetPassword = React.lazy(() => import("./user/ResetPassword"));
const Products = React.lazy(() => import("./product/Products"));
const Checkout = React.lazy(() => import("./order/Checkout"));
const Profile = React.lazy(() => import("./user/Profile"));
const SignUp = React.lazy(() => import("./user/SignUp"));
const LogIn = React.lazy(() => import("./user/LogIn"));
const Home = React.lazy(() => import("./home/Home"));

import { CurrencyProvider } from "./state/CurrencyContext";
import { AlertProvider } from "./state/AlertContext";

import "tailwindcss/tailwind.css";
import Spinner from "./components/Spinner";

import { loadStripe } from "@stripe/stripe-js";
import { Elements } from "@stripe/react-stripe-js";
import BluffCalledPage from "./components/BluffCalledPage";

const stripePromise = loadStripe(
  "pk_test_51KSkRQGXwpaEprvgD2FZ9X9uKYJ8vStU0PamJaKh592lWV4zzhMVXBbAKi8aRuwSb9SuDhKA39UNndIFUa2kgY0i00G9oZKxlW"
);

const App = () => {
  return (
    <Router>
      <AlertProvider>
        <CurrencyProvider>
          <StoreNavigation />
          <Suspense fallback={<Spinner />}>
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
              <Route exact path="/cart">
                <Cart />
              </Route>
              <Route path="/user/verify/:hash">
                <EmailVerified />
              </Route>
              <Route path="/resetpassword/:resetToken">
                <ResetPassword />
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
                <Elements stripe={stripePromise}>
                  <Checkout />
                </Elements>
              </Route>
              <Route path="/login">
                <LogIn />
              </Route>
              <Route path="/signup">
                <SignUp />
              </Route>
              <Route path="/forgotpassword">
                <ForgotPassword />
              </Route>
              <Route path="/bluff">
                <BluffCalledPage />
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
