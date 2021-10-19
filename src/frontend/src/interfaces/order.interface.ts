import Product from "./product.interface";

interface Order {
  id: number;
  status: "INACTIVE" | "ACTIVE" | "DISPATCHED" | "DELIVERED";
  userId: string;
  products: Product[];
}

export default Order;
