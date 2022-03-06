import Product from "../types/Product.type";
import Address from "./Address.interface";
import PaymentMethodDetails from "./PaymentMethodDetails.interface";

interface Order {
  id?: number;
  status?:
    | "USER_BROWSING"
    | "PENDING_PAYMENT"
    | "PROCESSING"
    | "DISPATCHED"
    | "DELIVERED";
  userId?: string;
  products: Product[];
  email?: string;
  phone?: string;
  shippingAddress?: Address;
  paymentMethodDetails?: PaymentMethodDetails;
  subtotal?: number;
  shippingCost?: number;
  totalCost?: number;
}

export default Order;
