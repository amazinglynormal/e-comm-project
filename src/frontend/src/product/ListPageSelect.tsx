import {
  ChevronDoubleRightIcon,
  ChevronDoubleLeftIcon,
} from "@heroicons/react/outline";

interface Props {
  pageChangeHandler: (newPage: number) => void;
  currentPage: number;
  totalPages: number;
}

const ListPageSelect = ({
  pageChangeHandler,
  currentPage,
  totalPages,
}: Props) => {
  const page = currentPage + 1;

  return (
    <div className="flex justify-between">
      <div>
        <button
          onClick={() => pageChangeHandler(currentPage - 1)}
          className="inline-flex items-center  justify-center space-x-1 px-4 h-10 border border-gray-300 rounded-md bg-white hover:bg-gray-100 focus:outline-none focus:border-indigo-600 focus:ring-2 focus:ring-offset-1 focus:ring-offset-indigo-600 focus:ring-indigo-600 focus:ring-opacity-25"
          disabled={currentPage === 0}
        >
          <ChevronDoubleLeftIcon className="h-4 w-5  text-gray-400" />
          <span>Previous</span>
        </button>
      </div>
      <div className="hidden space-x-2 sm:flex">
        {currentPage > 0 && (
          <button
            className="inline-flex items-center px-4 h-10 border border-gray-300 rounded-md bg-white hover:bg-gray-100 focus:outline-none focus:border-indigo-600 focus:ring-2 focus:ring-offset-1 focus:ring-offset-indigo-600 focus:ring-indigo-600 focus:ring-opacity-25"
            onClick={() => pageChangeHandler(currentPage - 1)}
          >
            {page - 1}
          </button>
        )}
        <button
          disabled
          className="inline-flex items-center px-4 h-10 border border-indigo-600 ring-1 ring-indigo-600 rounded-md bg-white hover:bg-gray-100 focus:outline-none focus:border-indigo-600 focus:ring-2 focus:ring-offset-1 focus:ring-offset-indigo-600 focus:ring-indigo-600 focus:ring-opacity-25"
        >
          {page}
        </button>
        {currentPage < totalPages && (
          <button
            className="inline-flex items-center px-4 h-10 border border-gray-300 rounded-md bg-white hover:bg-gray-100 focus:outline-none focus:border-indigo-600 focus:ring-2 focus:ring-offset-1 focus:ring-offset-indigo-600 focus:ring-indigo-600 focus:ring-opacity-25"
            onClick={() => pageChangeHandler(currentPage + 1)}
          >
            {page + 1}
          </button>
        )}
      </div>
      <div>
        <button
          onClick={() => pageChangeHandler(currentPage + 1)}
          className="inline-flex items-center justify-center space-x-1 px-4 h-10 border border-gray-300 rounded-md bg-white hover:bg-gray-100 focus:outline-none focus:border-indigo-600 focus:ring-2 focus:ring-offset-1 focus:ring-offset-indigo-600 focus:ring-indigo-600 focus:ring-opacity-25"
          disabled={currentPage === totalPages}
        >
          <span>Next</span>
          <ChevronDoubleRightIcon className="h-4 w-5 text-gray-400" />
        </button>
      </div>
    </div>
  );
};

export default ListPageSelect;
