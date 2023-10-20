import { useAppSelector, useAppDispatch } from "../hooks/redux-hooks";
import useProductDetailsSWR from "../hooks/useProductDetailsSWR";
import createOrder from "../state/async-thunks/createOrder";
import updateOrder from "../state/async-thunks/updateOrder";
import {
  selectOrder,
  setAsActiveOrder,
  updateOrderLocally,
} from "../state/orderSlice";
import { selectUser } from "../state/userSlice";
import { addProductToLocalStorageOrder } from "../utils/localStorageOrderUtils";

interface Props {
  productId: number;
}

const AddToCartButton = ({ productId }: Props) => {
  const activeOrder = useAppSelector(selectOrder);
  const dispatch = useAppDispatch();
  const { data } = useProductDetailsSWR(productId.toString());

  const user = useAppSelector(selectUser);

  const onClickHandler = () => {
    if (!user) {
      const order = addProductToLocalStorageOrder(data);
      if (!activeOrder) {
        dispatch(setAsActiveOrder(order));
      } else {
        dispatch(updateOrderLocally({ ...order }));
      }
    } else {
      if (!activeOrder) {
        dispatch(createOrder([productId]));
      } else {
        dispatch(updateOrder({ addProduct: productId }));
      }
    }
  };

  return (
    <button
      type="button"
      className="w-full bg-indigo-600 border border-transparent rounded-md py-3 px-8 flex items-center justify-center text-base font-medium text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-50 focus:ring-indigo-500"
      onClick={onClickHandler}
    >
      Add to bag
    </button>
  );
};

export default AddToCartButton;
