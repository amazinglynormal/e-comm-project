import classNames from "../utils/classNames";

const currencies = ["CAD", "USD", "AUD", "EUR", "GBP"];

interface Props {
  version: "desktop" | "mobile";
}

export const CurrencySelector = ({ version }: Props) => {
  return (
    <form>
      <div className={version === "mobile" ? "inline-block" : ""}>
        <label htmlFor={`${version}-currency`} className="sr-only">
          Currency
        </label>
        <div className="-ml-2 group relative bg-gray-900 border-transparent rounded-md focus-within:ring-2 focus-within:ring-white">
          <select
            id={`${version}-currency`}
            name="currency"
            className={classNames(
              version === "desktop"
                ? "bg-none bg-gray-900 text-white group-hover:text-gray-100"
                : "bg-none text-gray-900 group-hover:text-gray-900",
              "bg-none border-transparent rounded-md py-0.5 pl-2 pr-5 flex items-center text-sm font-medium  focus:outline-none focus:ring-0 focus:border-gray-900"
            )}
          >
            {currencies.map((currency) => (
              <option key={currency}>{currency}</option>
            ))}
          </select>
          <div className="absolute right-0 inset-y-0 flex items-center pointer-events-none">
            <svg
              aria-hidden="true"
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 20 20"
              className="w-5 h-5 text-gray-300"
            >
              <path
                stroke="currentColor"
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth="1.5"
                d="M6 8l4 4 4-4"
              />
            </svg>
          </div>
        </div>
      </div>
    </form>
  );
};
