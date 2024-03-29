import { QuestionMarkCircleIcon } from "@heroicons/react/solid";
import { Link } from "react-router-dom";
import { useCurrency } from "../state/CurrencyContext";
import { useAppSelector } from "../hooks/redux-hooks";
import { selectOrder } from "../state/orderSlice";

const currencySymbol: { [index: string]: string } = {
  eur: "€",
  gbp: "£",
  usd: "$",
};

interface Props {
  buttonText: string;
  onClickHandler: () => void;
}

const OrderCostSummary = ({ buttonText, onClickHandler }: Props) => {
  const { currency } = useCurrency();
  const order = useAppSelector(selectOrder);

  return (
    <section
      aria-labelledby="summary-heading"
      className="mt-10 bg-gray-50 rounded-lg px-4 py-6 sm:p-6 lg:p-8 lg:mt-0 lg:col-span-5"
    >
      <h2 id="summary-heading" className="text-lg font-medium text-gray-900">
        Order summary
      </h2>

      <dl className="mt-6 space-y-4">
        <div className="flex items-center justify-between">
          <dt className="text-sm text-gray-600">Subtotal</dt>
          <dd className="text-sm font-medium text-gray-900">{`${currencySymbol[currency]}${order?.subtotal}`}</dd>
        </div>
        <div className="border-t border-gray-200 pt-4 flex items-center justify-between">
          <dt className="flex items-center text-sm text-gray-600">
            <span>Shipping estimate</span>
            <Link
              to="/bluff"
              className="ml-2 flex-shrink-0 text-gray-400 hover:text-gray-500"
            >
              <span className="sr-only">
                Learn more about how shipping is calculated
              </span>
              <QuestionMarkCircleIcon className="h-5 w-5" aria-hidden="true" />
            </Link>
          </dt>
          <dd className="text-sm font-medium text-gray-900">{`${currencySymbol[currency]}${order?.shippingCost}`}</dd>
        </div>

        <div className="border-t border-gray-200 pt-4 flex items-center justify-between">
          <dt className="text-base font-medium text-gray-900">Order total</dt>
          <dd className="text-base font-medium text-gray-900">{`${currencySymbol[currency]}${order?.totalCost}`}</dd>
        </div>
      </dl>

      <div className="mt-6">
        <button
          type="button"
          className="w-full bg-indigo-600 border border-transparent rounded-md shadow-sm py-3 px-4 text-base font-medium text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-50 focus:ring-indigo-500"
          onClick={onClickHandler}
        >
          {buttonText}
        </button>
      </div>
    </section>
  );
};

export default OrderCostSummary;
