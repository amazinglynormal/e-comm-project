import { FormEvent } from "react";
import { useHistory } from "react-router";
import { Link } from "react-router-dom";
import { useAppSelector } from "../hooks/redux-hooks";
import { selectOrder } from "../state/orderSlice";
import CartItem from "./CartItem";
import OrderCostSummary from "./OrderCostSummary";

const EmptyCart = () => {
  return (
    <>
      <h3>Your shopping bag is currently empty!</h3>
      <Link
        to="/"
        className="text-indigo-700 hover:text-indigo-900 hover:underline"
      >
        Home
      </Link>
    </>
  );
};

const Cart = () => {
  const history = useHistory();

  const order = useAppSelector(selectOrder);

  const onSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    history.push("/checkout");
  };

  return (
    <div className="bg-white">
      <div className="max-w-2xl mx-auto pt-16 pb-24 px-4 sm:px-6 lg:max-w-7xl lg:px-8">
        <h1 className="text-3xl font-extrabold tracking-tight text-gray-900 sm:text-4xl">
          Shopping Bag
        </h1>
        <form
          onSubmit={onSubmit}
          className="mt-12 lg:grid lg:grid-cols-12 lg:gap-x-12 lg:items-start xl:gap-x-16"
        >
          <section aria-labelledby="cart-heading" className="lg:col-span-7">
            <h2 id="cart-heading" className="sr-only">
              Items in your shopping bag
            </h2>

            {order === undefined ||
            order === null ||
            order.products.length === 0 ? (
              <EmptyCart />
            ) : (
              <ul className="border-t border-b border-gray-200 divide-y divide-gray-200">
                {order.products.map((product, index) => (
                  <CartItem key={`${product.id}${index}`} product={product} />
                ))}
              </ul>
            )}
          </section>

          <OrderCostSummary
            orderProducts={order?.products || []}
            buttonText="Go to checkout"
          />
        </form>
      </div>
    </div>
  );
};

export default Cart;
