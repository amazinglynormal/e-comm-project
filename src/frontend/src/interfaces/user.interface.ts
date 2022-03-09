import Address from "./Address.interface";
import Order from "./order.interface";

interface User {
  id: string;
  username: string;
  email: string;
  role: "CUSTOMER";
  orders: Order[];
  address?: Address;
}

export default User;
