import Product from "../types/Product.type";

interface Order {
  id: number;
  status: "INACTIVE" | "ACTIVE" | "DISPATCHED" | "DELIVERED";
  userId: string;
  products: Product[];
}

export default Order;
