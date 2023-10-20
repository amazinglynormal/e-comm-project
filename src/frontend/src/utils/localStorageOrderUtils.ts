import Order from "../interfaces/order.interface";
import Product from "../types/Product.type";

const KEY = "locallySavedOrder";

function checkLocalStorageForExistingOrder(): Order | null {
  const order = localStorage.getItem(KEY);

  if (order) {
    const parsedOrder: Order = JSON.parse(order);
    return parsedOrder;
  }

  return null;
}

function storeOrderInLocalStorage(order: Order): void {
  const stringifiedOrder = JSON.stringify(order);
  localStorage.setItem(KEY, stringifiedOrder);
}

function addProductToLocalStorageOrder(product: Product): Order {
  const storedOrder = localStorage.getItem(KEY);
  let order: Order;

  if (storedOrder !== null) {
    order = JSON.parse(storedOrder!);
  } else {
    order = { products: [] };
  }

  order.products.push(product);
  let subtotal = order.products.reduce((acc, prod) => acc + prod.eur, 0);
  const shippingCost = subtotal < 50 ? 0.0 : 9.99;
  const totalCost = subtotal + shippingCost;

  order.subtotal = subtotal;
  order.shippingCost = shippingCost;
  order.totalCost = totalCost;

  const stringifiedOrder = JSON.stringify(order);

  localStorage.setItem(KEY, stringifiedOrder);

  return order;
}

function removeProductFromLocalStorageOrder(product: Product): Order {
  const storedOrder = localStorage.getItem(KEY);
  const order: Order = JSON.parse(storedOrder!);

  const indexOfProduct = order.products.findIndex((p) => p.id === product.id);

  if (indexOfProduct === -1) {
    return order;
  }

  order.products.splice(indexOfProduct, 1);

  let subtotal = order.products.reduce((acc, prod) => acc + prod.eur, 0);
  const shippingCost = subtotal < 50 ? 0.0 : 9.99;
  const totalCost = subtotal + shippingCost;

  order.subtotal = subtotal;
  order.shippingCost = shippingCost;
  order.totalCost = totalCost;

  const stringifiedOrder = JSON.stringify(order);

  localStorage.setItem(KEY, stringifiedOrder);

  return order;
}

function deleteOrderFromLocalStorage(): void {
  localStorage.removeItem(KEY);
}

export {
  checkLocalStorageForExistingOrder,
  storeOrderInLocalStorage,
  addProductToLocalStorageOrder,
  removeProductFromLocalStorageOrder,
  deleteOrderFromLocalStorage,
};
