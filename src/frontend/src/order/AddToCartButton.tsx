import { useAppSelector, useAppDispatch } from "../hooks/redux-hooks";
import useProductDetailsSWR from "../hooks/useProductDetailsSWR";
import createOrder from "../state/async-thunks/createOrder";
import updateOrder from "../state/async-thunks/updateOrder";
import {
  addProductToOrder,
  selectOrder,
  setAsActiveOrder,
} from "../state/orderSlice";
import { selectUser } from "../state/userSlice";
import {
  addProductToLocalStorageOrder,
  checkLocalStorageForExistingOrder,
} from "../utils/localStorageOrderUtils";

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
      if (!activeOrder) {
        const existingOrder = checkLocalStorageForExistingOrder();

        if (!existingOrder) {
          addProductToLocalStorageOrder(data);
          dispatch(setAsActiveOrder({ products: [data] }));
        } else {
          const updatedOrder = addProductToLocalStorageOrder(data);
          dispatch(setAsActiveOrder(updatedOrder));
        }
      } else {
        addProductToLocalStorageOrder(data);
        dispatch(addProductToOrder(data));
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
