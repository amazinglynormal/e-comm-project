import { useAppDispatch } from "../hooks/redux-hooks";

import { XIcon } from "@heroicons/react/solid";
import updateOrder from "../state/async-thunks/updateOrder";

interface Props {
  productId: number;
}

const RemoveFromCartButton = ({ productId }: Props) => {
  const dispatch = useAppDispatch();

  const onClickHandler = () => {
    dispatch(updateOrder({ removeProduct: productId }));
  };

  return (
    <button
      type="button"
      className="-m-2 p-2 inline-flex text-gray-400 hover:text-gray-500"
      onClick={onClickHandler}
    >
      <span className="sr-only">Remove</span>
      <XIcon className="h-5 w-5" aria-hidden="true" />
    </button>
  );
};

export default RemoveFromCartButton;
