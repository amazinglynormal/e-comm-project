import Order from "./order.interface";

interface User {
  id: string;
  username: string;
  email: string;
  role: string;
  orders: Order[];
}

export default User;
