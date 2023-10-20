import { useAppDispatch, useAppSelector } from "../hooks/redux-hooks";
import { resetActiveOrder, selectCompletedOrder } from "../state/orderSlice";
import { deleteOrderFromLocalStorage } from "../utils/localStorageOrderUtils";
import Spinner from "../components/Spinner";
import { useCurrency } from "../state/CurrencyContext";
import { Link } from "react-router-dom";

const currencySymbol: { [index: string]: string } = {
  eur: "€",
  gbp: "£",
  usd: "$",
};

const OrderSummary = () => {
  const dispatch = useAppDispatch();
  const { currency } = useCurrency();

  const order = useAppSelector(selectCompletedOrder);

  dispatch(resetActiveOrder());
  deleteOrderFromLocalStorage();

  return (
    <div className="bg-white">
      <div className="max-w-3xl mx-auto px-4 py-16 sm:px-6 sm:py-24 lg:px-8">
        <div className="max-w-xl">
          <h1 className="text-sm font-semibold uppercase tracking-wide text-indigo-600">
            Thank you!
          </h1>
          <p className="mt-2 text-4xl font-extrabold tracking-tight sm:text-5xl">
            It&apos;s on the way!
          </p>
          {order?.id && (
            <p className="mt-2 text-base text-gray-500">
              {`Your order #${order.id} has shipped and will be with you soon.`}
            </p>
          )}
        </div>

        <div className="mt-10 border-t border-gray-200">
          <h2 className="sr-only">Your order</h2>

          <h3 className="sr-only">Items</h3>
          {order &&
            order.products.map((product) => (
              <div
                key={product.id}
                className="py-10 border-b border-gray-200 flex space-x-6"
              >
                <img
                  src={product.imageSrc}
                  alt={product.imageAlt}
                  className="flex-none w-20 h-20 object-center object-cover bg-gray-100 rounded-lg sm:w-40 sm:h-40"
                />
                <div className="flex-auto flex flex-col">
                  <div>
                    <h4 className="font-medium text-gray-900">
                      <Link to={`/product/details/${product.id}`}>
                        {product.name}
                      </Link>
                    </h4>
                    <p className="mt-2 text-sm text-gray-600">
                      {product.description}
                    </p>
                  </div>
                  <div className="mt-6 flex-1 flex items-end">
                    <dl className="flex text-sm divide-x divide-gray-200 space-x-4 sm:space-x-6">
                      <div className="flex">
                        <dt className="font-medium text-gray-900">Price</dt>
                        <dd className="ml-2 text-gray-700">
                          {`${currencySymbol[currency]} ${product[currency]}`}
                        </dd>
                      </div>
                    </dl>
                  </div>
                </div>
              </div>
            ))}

          <div className="sm:ml-40 sm:pl-6">
            <h3 className="sr-only">Your information</h3>

            <h4 className="sr-only">Addresses</h4>
            <dl className="grid grid-cols-2 gap-x-6 text-sm py-10">
              <div>
                <dt className="font-medium text-gray-900">Shipping address</dt>
                <dd className="mt-2 text-gray-700">
                  <address className="not-italic">
                    {order?.shippingAddress?.name && (
                      <span className="block">
                        {order.shippingAddress.name}
                      </span>
                    )}
                    {order?.shippingAddress?.line1 && (
                      <span className="block">
                        {order.shippingAddress.line1}
                      </span>
                    )}
                    {order?.shippingAddress?.line2 && (
                      <span className="block">
                        {order.shippingAddress.line2}
                      </span>
                    )}
                    {order?.shippingAddress?.line3 && (
                      <span className="block">
                        {order.shippingAddress.line3}
                      </span>
                    )}
                    {order?.shippingAddress?.city && (
                      <span className="block">
                        {order.shippingAddress.city}
                      </span>
                    )}
                    {order?.shippingAddress?.province && (
                      <span className="block">
                        {order.shippingAddress.province}
                      </span>
                    )}
                    {order?.shippingAddress?.country && (
                      <span className="block">
                        {order.shippingAddress.country}
                      </span>
                    )}
                    {order?.shippingAddress?.zipCode && (
                      <span className="block">
                        {order.shippingAddress.zipCode}
                      </span>
                    )}
                  </address>
                </dd>
              </div>
            </dl>

            <h4 className="sr-only">Payment</h4>
            <dl className="grid grid-cols-2 gap-x-6 border-t border-gray-200 text-sm py-10">
              <div>
                <dt className="font-medium text-gray-900">Expected delivery</dt>
                <dd className="mt-2 text-gray-700">
                  <p>
                    Takes up to 7 working days or whenever we get around to it
                  </p>
                </dd>
              </div>
            </dl>

            <h3 className="sr-only">Summary</h3>

            <dl className="space-y-6 border-t border-gray-200 text-sm pt-10">
              {/* {order?.subtotal && order.shippingCost && order.totalCost ? (
                <>
                  <div className="flex justify-between">
                    <dt className="font-medium text-gray-900">Subtotal</dt>
                    <dd className="text-gray-700">{`${currencySymbol[currency]}${order?.subtotal}`}</dd>
                  </div>

                  <div className="flex justify-between">
                    <dt className="font-medium text-gray-900">Shipping</dt>
                    <dd className="text-gray-700">{`${currencySymbol[currency]}${order?.shippingCost}`}</dd>
                  </div>
                  <div className="flex justify-between">
                    <dt className="font-medium text-gray-900">Total</dt>
                    <dd className="text-gray-900">{`${currencySymbol[currency]}${order?.totalCost}`}</dd>
                  </div>
                </>
              ) : (
                <Spinner />
              )} */}
              <div className="flex justify-between">
                <dt className="font-medium text-gray-900">Subtotal</dt>
                <dd className="text-gray-700">{`${currencySymbol[currency]}${order?.subtotal}`}</dd>
              </div>

              <div className="flex justify-between">
                <dt className="font-medium text-gray-900">Shipping</dt>
                <dd className="text-gray-700">{`${currencySymbol[currency]}${order?.shippingCost}`}</dd>
              </div>
              <div className="flex justify-between">
                <dt className="font-medium text-gray-900">Total</dt>
                <dd className="text-gray-900">{`${currencySymbol[currency]}${order?.totalCost}`}</dd>
              </div>
            </dl>
          </div>
        </div>
      </div>
    </div>
  );
};

export default OrderSummary;
