import { InformationCircleIcon } from "@heroicons/react/outline";
import { Link } from "react-router-dom";

interface Props {
  header?: string;
  message?: string;
  linkTo?: string;
}

const NoResultsFound = ({ header, message, linkTo }: Props) => {
  return (
    <div>
      <div className="rounded-full h-20 w-20 flex justify-center content-center">
        <InformationCircleIcon className="h-10 w-10" />
      </div>
      <h1>{header || "No results found"}</h1>
      {message && <p>{message}</p>}
      <Link to={linkTo || "/"}>&larr; Go back</Link>
    </div>
  );
};

export default NoResultsFound;
