import { useAppSelector, useAppDispatch } from "../hooks/redux-hooks";
import createOrder from "../state/async-thunks/createOrder";
import updateOrder from "../state/async-thunks/updateOrder";
import { selectOrder } from "../state/orderSlice";
import { selectUser } from "../state/userSlice";

interface Props {
  productId: number;
}

const AddToCartButton = ({ productId }: Props) => {
  const activeOrder = useAppSelector(selectOrder);
  const dispatch = useAppDispatch();

  const user = useAppSelector(selectUser);

  const onClickHandler = () => {
    if (!activeOrder) {
      dispatch(createOrder(productId));
    } else {
      dispatch(updateOrder({ addProduct: productId }));
    }
  };

  return (
    <button
      type="button"
      className="w-full bg-indigo-600 border border-transparent rounded-md py-3 px-8 flex items-center justify-center text-base font-medium text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-50 focus:ring-indigo-500"
      onClick={onClickHandler}
      disabled={user?.role === "GUEST" || undefined}
    >
      Add to bag
    </button>
  );
};

export default AddToCartButton;
