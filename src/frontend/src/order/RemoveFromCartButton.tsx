import { removeProductFromLocalStorageOrder } from "../utils/localStorageOrderUtils";
import { removeProductFromOrder } from "../state/orderSlice";
import { useAppDispatch, useAppSelector } from "../hooks/redux-hooks";
import updateOrder from "../state/async-thunks/updateOrder";
import { selectUser } from "../state/userSlice";
import { XIcon } from "@heroicons/react/solid";
import Product from "../types/Product.type";

interface Props {
  product: Product;
}

const RemoveFromCartButton = ({ product }: Props) => {
  const dispatch = useAppDispatch();

  const user = useAppSelector(selectUser);

  const onClickHandler = () => {
    if (!user) {
      removeProductFromLocalStorageOrder(product);
      dispatch(removeProductFromOrder(product));
    } else {
      dispatch(updateOrder({ removeProduct: product.id }));
    }
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
