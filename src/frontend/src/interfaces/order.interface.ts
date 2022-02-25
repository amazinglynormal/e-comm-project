import Product from "../types/Product.type";

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
}

export default Order;
