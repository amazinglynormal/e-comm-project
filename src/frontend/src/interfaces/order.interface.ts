import Product from "../types/Product.type";
import Address from "./Address.interface";

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
  subtotal?: number;
  shippingCost?: number;
  totalCost?: number;
}

export default Order;
