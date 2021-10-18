import Product from "./product.interface";

interface Order {
  id: number;
  status: "ACTIVE" | "DISPATCHED" | "DELIVERED";
  userId: string;
  products: Product[];
}

export default Order;
