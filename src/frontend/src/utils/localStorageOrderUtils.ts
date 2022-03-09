import Order from "../interfaces/order.interface";
import Product from "../types/Product.type";

const KEY = "locallySavedOrder";

function checkLocalStorageForExistingOrder(): Order | null {
  const order = localStorage.getItem(KEY);

  if (order !== null) {
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
