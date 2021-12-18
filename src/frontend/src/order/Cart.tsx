import { useHistory, useParams } from "react-router";
import { Link } from "react-router-dom";
import { useAppSelector } from "../hooks/redux-hooks";
import { selectOrder } from "../state/orderSlice";
import Product from "../types/Product.type";
import CartItem from "./CartItem";
import CartSummary from "./CartSummary";

const products = [
  {
    id: 1,
    name: "Basic Tee",
    eur: 32.99,
    usd: 32.99,
    gbp: 32.99,
    color: "red",
    size: "L",
    imageSrc:
      "https://tailwindui.com/img/ecommerce-images/shopping-cart-page-01-product-01.jpg",
    imageAlt: "Front of men's Basic Tee in sienna.",
    inStock: true,
    categoryId: 1,
    description: "test description",
  },
  {
    id: 2,
    name: "Basic Tee",
    eur: 32.99,
    usd: 32.99,
    gbp: 32.99,
    color: "blue",
    size: "L",
    imageSrc:
      "https://tailwindui.com/img/ecommerce-images/shopping-cart-page-01-product-02.jpg",
    imageAlt: "Front of men's Basic Tee in black.",
    inStock: true,
    categoryId: 1,
    description: "test description",
  },
  {
    id: 3,
    name: "Nomad Tumbler",
    eur: 32.99,
    usd: 32.99,
    gbp: 32.99,
    color: "black",
    size: "L",
    imageSrc:
      "https://tailwindui.com/img/ecommerce-images/shopping-cart-page-01-product-03.jpg",
    imageAlt: "Insulated bottle with white base and black snap lid.",
    inStock: false,
    categoryId: 1,
    description: "test description",
  },
] as Product[];

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

interface CartRouteParams {
  orderId: string;
}

const Cart = () => {
  const history = useHistory();
  const { orderId } = useParams<CartRouteParams>();

  console.log(orderId);

  const order = useAppSelector(selectOrder);

  const onSubmit = () => {
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

            {order === undefined || order.products.length === 0 ? (
              <EmptyCart />
            ) : (
              <ul className="border-t border-b border-gray-200 divide-y divide-gray-200">
                {products.map((product) => (
                  <CartItem key={product.id} product={product} />
                ))}
              </ul>
            )}
          </section>

          {/* Order summary */}
          <CartSummary orderProducts={order?.products || []} />
        </form>
      </div>
    </div>
  );
};

export default Cart;
