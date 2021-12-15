import { CheckIcon, XIcon } from "@heroicons/react/solid";
import { Link } from "react-router-dom";
import { useCurrency } from "../state/CurrencyContext";
import Product from "../types/Product.type";
import RemoveFromCartButton from "./RemoveFromCartButton";

interface Props {
  product: Product;
}

const currencySymbol: { [index: string]: string } = {
  eur: "€",
  gbp: "£",
  usd: "$",
};

const CartItem = ({ product }: Props) => {
  const { currency } = useCurrency();

  return (
    <li key={product.id} className="flex py-6 sm:py-10">
      <div className="flex-shrink-0">
        <img
          src={product.imageSrc}
          alt={product.imageAlt}
          className="w-24 h-24 rounded-md object-center object-cover sm:w-48 sm:h-48"
        />
      </div>

      <div className="ml-4 flex-1 flex flex-col justify-between sm:ml-6">
        <div className="relative pr-9 sm:grid sm:grid-cols-2 sm:gap-x-6 sm:pr-0">
          <div>
            <div className="flex justify-between">
              <h3 className="text-sm">
                <Link
                  to={`/product/details/${product.id}`}
                  className="font-medium text-gray-700 hover:text-gray-800"
                >
                  {product.name}
                </Link>
              </h3>
            </div>
            <div className="mt-1 flex text-sm">
              <p className="text-gray-500">{product.color}</p>
              {product.size ? (
                <p className="ml-4 pl-4 border-l border-gray-200 text-gray-500">
                  {product.size}
                </p>
              ) : null}
            </div>
            <p className="mt-1 text-sm font-medium text-gray-900">
              {`${currencySymbol[currency]}${product[currency]}`}
            </p>
          </div>

          <div className="mt-4 sm:mt-0 sm:pr-9">
            <div className="absolute top-0 right-0">
              <RemoveFromCartButton productId={product.id} />
            </div>
          </div>
        </div>

        <p className="mt-4 flex text-sm text-gray-700 space-x-2">
          {product.inStock ? (
            <CheckIcon
              className="flex-shrink-0 h-5 w-5 text-green-500"
              aria-hidden="true"
            />
          ) : (
            <XIcon
              className="flex-shrink-0 h-5 w-5 text-red-800"
              aria-hidden="true"
            />
          )}

          <span>{product.inStock ? "In stock" : "Out of stock"}</span>
        </p>
      </div>
    </li>
  );
};

export default CartItem;
