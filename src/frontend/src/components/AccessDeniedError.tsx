import { Link } from "react-router-dom";

interface Props {
  message: string;
  linkTo: string;
}

const AccessDeniedError = ({ message, linkTo }: Props) => {
  return (
    <div className="py-16">
      <div className="text-center">
        <p className="text-sm font-semibold text-indigo-600 uppercase tracking-wide">
          401 error
        </p>
        <h1 className="mt-2 text-4xl font-extrabold text-gray-900 tracking-tight sm:text-5xl">
          Access denied.
        </h1>
        <p className="mt-2 text-base text-gray-500">{message}</p>
        <div className="mt-6">
          <Link
            to={linkTo}
            className="text-base font-medium text-indigo-600 hover:text-indigo-500"
          >
            Go to log in<span aria-hidden="true"> &rarr;</span>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default AccessDeniedError;
