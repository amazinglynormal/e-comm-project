import Order from "./order.interface";

interface User {
  id: string;
  username: string;
  email: string;
  role: "GUEST" | "CUSTOMER";
  orders: Order[];
}

export default User;