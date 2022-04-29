import AccessDeniedError from "../components/AccessDeniedError";
import NoResultsFound from "../components/NoResultsFound";
import { useAppSelector } from "../hooks/redux-hooks";
import { useCurrency } from "../state/CurrencyContext";
import { selectUser } from "../state/userSlice";
import OrderHistoryListItem from "./OrderHistoryListItem";

const OrderHistory = () => {
  const user = useAppSelector(selectUser);
  const { currency } = useCurrency();

  if (!user) {
    return (
      <AccessDeniedError
        message="Sorry, you must be logged in to view your order history."
        linkTo="/login"
      />
    );
  }

  const sortedOrders = user.orders.sort(function (a, b) {
    return Date.parse(b.datePlaced) - Date.parse(a.datePlaced);
  });

  return (
    <div className="bg-white">
      <div className="max-w-7xl mx-auto py-16 px-4 sm:px-6 lg:pb-24 lg:px-8">
        <div className="max-w-xl">
          <h1 className="text-2xl font-extrabold tracking-tight text-gray-900 sm:text-3xl">
            Order history
          </h1>
          <p className="mt-2 text-sm text-gray-500">
            Check the status of recent orders, manage returns, and download
            invoices.
          </p>
        </div>

        <div className="mt-16">
          <h2 className="sr-only">Recent orders</h2>

          {sortedOrders.length <= 0 ? (
            <NoResultsFound />
          ) : (
            <div className="space-y-20">
              {sortedOrders.map((order) => (
                <OrderHistoryListItem
                  key={order.id}
                  order={order}
                  currency={currency}
                />
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default OrderHistory;
