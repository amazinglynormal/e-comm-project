import { Link } from "react-router-dom";
import Currency from "../enums/Currency.enum";
import Order from "../interfaces/order.interface";

interface Props {
  order: Order;
  currency: Currency;
}

const currencySymbol: { [index: string]: string } = {
  eur: "€",
  gbp: "£",
  usd: "$",
};

const OrderHistoryListItem = ({ order, currency }: Props) => {
  return (
    <div>
      <h3 className="sr-only">
        Order placed on{" "}
        <time dateTime={order.datePlaced}>{order.datePlaced}</time>
      </h3>

      <div className="bg-gray-50 rounded-lg py-6 px-4 sm:px-6 sm:flex sm:items-center sm:justify-between sm:space-x-6 lg:space-x-8">
        <dl className="divide-y divide-gray-200 space-y-6 text-sm text-gray-600 flex-auto sm:divide-y-0 sm:space-y-0 sm:grid sm:grid-cols-3 sm:gap-x-6 lg:w-1/2 lg:flex-none lg:gap-x-8">
          <div className="flex justify-between sm:block">
            <dt className="font-medium text-gray-900">Date placed</dt>
            <dd className="sm:mt-1">
              <time dateTime={order.datePlaced}>{order.datePlaced}</time>
            </dd>
          </div>
          <div className="flex justify-between pt-6 sm:block sm:pt-0">
            <dt className="font-medium text-gray-900">Order number</dt>
            <dd className="sm:mt-1">{order.id!}</dd>
          </div>
          <div className="flex justify-between pt-6 font-medium text-gray-900 sm:block sm:pt-0">
            <dt>Total amount</dt>
            <dd className="sm:mt-1">{order.totalCost}</dd>
          </div>
        </dl>
      </div>

      <table className="mt-4 w-full text-gray-500 sm:mt-6">
        <caption className="sr-only">Products</caption>
        <thead className="sr-only text-sm text-gray-500 text-left sm:not-sr-only">
          <tr>
            <th scope="col" className="sm:w-2/5 lg:w-1/3 pr-8 py-3 font-normal">
              Product
            </th>
            <th
              scope="col"
              className="hidden w-1/5 pr-8 py-3 font-normal sm:table-cell"
            >
              Price
            </th>
            <th
              scope="col"
              className="hidden pr-8 py-3 font-normal sm:table-cell"
            >
              Status
            </th>
            <th scope="col" className="w-0 py-3 font-normal text-right">
              Info
            </th>
          </tr>
        </thead>
        <tbody className="border-b border-gray-200 divide-y divide-gray-200 text-sm sm:border-t">
          {order.products.map((product) => (
            <tr key={product.id}>
              <td className="py-6 pr-8">
                <div className="flex items-center">
                  <img
                    src={product.imageSrc}
                    alt={product.imageAlt}
                    className="w-16 h-16 object-center object-cover rounded mr-6"
                  />
                  <div>
                    <div className="font-medium text-gray-900">
                      {product.name}
                    </div>
                    <div className="mt-1 sm:hidden">{`${currencySymbol[currency]}${product[currency]}`}</div>
                  </div>
                </div>
              </td>
              <td className="hidden py-6 pr-8 sm:table-cell">
                {`${currencySymbol[currency]}${product[currency]}`}
              </td>
              <td className="hidden py-6 pr-8 sm:table-cell">{order.status}</td>
              <td className="py-6 font-medium text-right whitespace-nowrap">
                <Link
                  to={`/product/details/${product.id}`}
                  className="text-indigo-600"
                >
                  View
                  <span className="hidden lg:inline"> Product</span>
                  <span className="sr-only">, {product.name}</span>
                </Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default OrderHistoryListItem;
