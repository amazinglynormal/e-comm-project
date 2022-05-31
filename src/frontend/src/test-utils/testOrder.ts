import products from "./testProducts";
import testAddress from "./testAddress";
import testPaymentMethodDetails from "./testPaymentMethodDetails";
import Order from "../interfaces/order.interface";

const testOrder: Order = {
  id: 1,
  status: "DELIVERED",
  userId: "a1b2c3d4e5",
  products: products,
  datePlaced: "2022-05-31T12:32:45.877+00:00",
  email: "test@test.com",
  phone: "0871234567",
  shippingAddress: testAddress,
  paymentMethodDetails: testPaymentMethodDetails,
  subtotal: 45.96,
  shippingCost: 9.99,
  totalCost: 55.95,
};

export default testOrder;
